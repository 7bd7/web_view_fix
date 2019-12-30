package team.deepvision.webviewfix

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_sschool_selection_tb.*
import team.deepvision.webviewfix.data.Repo
import team.deepvision.webviewfix.data.SSchoolDay
import team.deepvision.webviewfix.data.SSchoolDayUserData
import team.deepvision.webviewfix.data.SSchoolHighlight

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var nScroll: NestedScrollView
    private lateinit var jsInterface: AndroidContent
    private lateinit var dayData: SSchoolDay
    private val colorAdapter = ColorAdapter()
    private lateinit var repo: Repo

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repo = Repo(this)

        setUpWebView()
        setUpRv()

        // Mock SSchoolDay data
        dayData = getFakeData()

        setDayContent(dayData.content)
        dayData.userData.highlights.forEach {
            jsHighlightText(it)
        }

        // FOR TESTING
//        webView.postDelayed({
//            jsInterface.onTextSelected("", "", "", "")
//        }, 8000)
    }

    private fun setUpRv() {
        val rv = sschool_selection_tb_rv
        rv.adapter = colorAdapter
        colorAdapter.data = SSchoolHighlight.HighlightingColor.values().asList()
        rv.setHasFixedSize(true)
    }

    private fun setDayContent(content: String) {
        jsInterface = AndroidContent(content)
        webView.addJavascriptInterface(jsInterface, "AndroidContent")
        webView.loadUrl("file:///android_asset/index.html")
    }

    private fun jsHighlightText(highlighting: SSchoolHighlight) {
        webView.evaluateJavascript("javascript: onAndroidHighlightText({$highlighting.selectedText}, " +
                "{$highlighting.componentId}, {$highlighting.index}, {$highlighting.length})", null)
        Toast.makeText(this, "Highlighting with ${highlighting.color.name} color passed to js", Toast.LENGTH_LONG).show()
    }

    private fun jsOnScrollUpdate(scrollY: Int) {
        webView.evaluateJavascript("javascript: updateFromAndroid($scrollY)", null)
    }

    inner class AndroidContent(private val content: String) {

        @JavascriptInterface
        fun get() = content

        @JavascriptInterface
        fun getHeight() = Resources.getSystem().displayMetrics.heightPixels

        @JavascriptInterface
        fun yScrollTo(position: Int) {
            nScroll.smoothScrollTo(0, position)
        }

        @JavascriptInterface
        fun onTextSelected(selectedText: String, componentId: String, index: String, length: String) {
            openSelectionToolbar(SSchoolHighlight(SSchoolHighlight.HighlightingColor.NOT_SELECTED,
                selectedText, dayData.id, componentId, index, length))
        }

    }

    private fun openSelectionToolbar(selection: SSchoolHighlight) {
        main_selection_tb.visibility = View.VISIBLE
        colorAdapter.selectedColorValue = selection.color.argb
        colorAdapter.listener = {
            val result = selection.copy(color = it)
            repo.saveHighlighting(result)
            jsHighlightText(result)
            main_selection_tb.visibility = View.GONE
        }
    }

    private fun getFakeData(): SSchoolDay {
        return SSchoolDay(
            "2020:1:1:1", "2020:1:1", "DAY 1 TITLE",
            "2020-01-01",
            "https://dv-dev.fra1.digitaloceanspaces.com/ssplus/sschool/day_cover.png",
            application.assets.open("content.json").bufferedReader().use { it.readText() },
            SSchoolDayUserData((repo.getHighlights())))
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView = findViewById(R.id.sschool_webview)
        nScroll = findViewById(R.id.sschool_nscroll)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webChromeClient = object : WebChromeClient() {

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.apply {
                    Log.i(
                        "WebViewLogger",
                        "${message()} -- From line ${lineNumber()} of ${sourceId()}"
                    )
                }
                return true
            }
        }

        nScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            jsOnScrollUpdate(scrollY)
        })
    }

}
