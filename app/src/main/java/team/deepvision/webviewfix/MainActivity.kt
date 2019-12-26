package team.deepvision.webviewfix

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import team.deepvision.webviewfix.data.SSchoolDay
import team.deepvision.webviewfix.data.SSchoolDayUserData
import team.deepvision.webviewfix.data.SSchoolHighlight

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var nScroll: NestedScrollView
    private lateinit var jsInterface: AndroidContent
    private lateinit var dayData: SSchoolDay

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpWebView()

        // Mock SSchoolDay
        dayData = getFakeData()

        setDayContent(dayData.content)

        dayData.userData.highlights.forEach { jsHighlightText(it) }
    }

    private fun setDayContent(content: String) {
        jsInterface = AndroidContent(content)
        webView.addJavascriptInterface(jsInterface, "AndroidContent")
        webView.loadUrl("file:///android_asset/index.html")
    }

    private fun jsHighlightText(highlighting: SSchoolHighlight) {
        webView.evaluateJavascript("javascript: onAndroidHighlightText({$highlighting.selectedText}, " +
                "{$highlighting.componentId}, {$highlighting.index}, {$highlighting.length})", null)
    }

    private fun jsOnScrollUpdate(scrollY: Int) {
        webView.evaluateJavascript("javascript: onAndroidScrollUpdate($scrollY)", null)
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
            openSelectionToolbar(selectedText, componentId, index, length)
        }

    }

    private fun openSelectionToolbar(selectedText: String, componentId: String, index: String, length: String) {
        // open toolbar
        // set ready for highlighting event
    }

    private fun saveData(selectedText: String, componentId: String, dayId: Int, index: String, length: String) {
        // save to persistent storage
    }

    private fun getFakeData(): SSchoolDay {
        return SSchoolDay(
            "2020:1:1:1", "2020:1:1", "DAY 1 TITLE",
            "2020-01-01",
            "https://dv-dev.fra1.digitaloceanspaces.com/ssplus/sschool/day_cover.png",
            application.assets.open("content.json").bufferedReader().use { it.readText() },
            SSchoolDayUserData(
                (listOf(
                    SSchoolHighlight(SSchoolHighlight.HighlightingColor.VIOLET,
                        "MOCK TEXT!!!!!!!", "2020:1:1:1",
                        "MOCK ID !!!", "1", "10")))))
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
