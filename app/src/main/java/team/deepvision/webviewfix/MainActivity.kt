package team.deepvision.webviewfix

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import com.google.gson.GsonBuilder
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
    private val gson = GsonBuilder()
        .registerTypeAdapter(SSchoolHighlight.HighlightingColor::class.java, SSchoolHighlight.HighlightingColor.Serializer())
        .registerTypeAdapter(SSchoolHighlight.HighlightingColor::class.java, SSchoolHighlight.HighlightingColor.Deserializer())
        .create()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repo = Repo(this)

        setUpWebView()
        setUpRv()
        school_selection_tb_back_iv.setOnClickListener {
            hideSelectionToolbar()
            jsDeselectText()
        }

        // Mock SSchoolDay data
        dayData = getFakeData()

        setDayContent(dayData.content)
    }

    @Suppress("unused")
    inner class AndroidContent(private val content: String) {

        /**
         *  Allows JS code to obtain content of the sschool day in JSON format
         */
        @JavascriptInterface
        fun get() = content

        /**
         *  Allows JS code to obtain content of the the height of the screen (in pixels, excluding navigation bar height)
         */
        @JavascriptInterface
        fun getWindowHeight() = Resources.getSystem().displayMetrics.heightPixels / Resources.getSystem().displayMetrics.density;

        /**
         *  Allows JS code to pass scroll event to Android, for Y axis
         */
        @JavascriptInterface
        fun yScrollTo(position: Int) {
            nScroll.smoothScrollTo(0, (position * Resources.getSystem().displayMetrics.density).toInt())
        }

        @JavascriptInterface
        fun yScrollOffset() = nScroll.computeVerticalScrollOffset() / Resources.getSystem().displayMetrics.density

        /**
         *  Allows JS code to pass text selection event to Android. Triggers selection toolbar opening.
         */
        @JavascriptInterface
        fun onTextSelected(selectedText: String, id: String, componentId: String, index: Int, length: Int) {
            runOnUiThread {
                openSelectionToolbar(
                    SSchoolHighlight(
                        selectedText, id, componentId, index, length, SSchoolHighlight.HighlightingColor.NOT_SELECTED, dayData.id
                    )
                )
            }
        }

        @JavascriptInterface
        fun onReady() {
            Log.i("AndroidContent", "Ready")
            runOnUiThread {
                jsHighlightText(dayData.userData.highlights)
            }
        }

        @JavascriptInterface
        fun onDeselectText() {
            hideSelectionToolbar()
        }

        @JavascriptInterface
        fun onHighlightedTextClicked(selectedText: String, id: String, componentId: String, index: Int, length: Int) {
            Log.wtf("AndroidContent", "onHighlightedTextClicked($selectedText)")
            val highlighting = repo.getHighlight(id)
            runOnUiThread { openSelectionToolbar(highlighting) }
        }

        @JavascriptInterface
        fun hideToolbar() {
            hideSelectionToolbar()
        }

    }

    /**
     *  Passes scroll updated (in Y axis) to JS code. The JS function shout has exactly the same signature
     *  as in this function.
     */
    private fun jsOnScrollUpdate(scrollY: Int) {
        val windowScrollY = scrollY / Resources.getSystem().displayMetrics.density;
        webView.evaluateJavascript("javascript: onAndroidScrollUpdate($windowScrollY)", null)
    }

    /**
     *  Passes on highlight complete event to JS code. The JS function shout has exactly the same signature
     *  as in this function. The last "highlighting.color.argb" parameter is the color of highlighting in RGB representation.
     */
    private fun jsHighlightText(highlightingList: List<SSchoolHighlight>) {
        webView.evaluateJavascript("javascript: onAndroidHighlightText(${gson.toJson(highlightingList)})", null)
    }

    private fun jsDeselectText() {
        webView.evaluateJavascript("javascript: onAndroidDeselectText()", null)
    }

    private fun setDayContent(content: String) {
        jsInterface = AndroidContent(content)
        webView.addJavascriptInterface(jsInterface, "AndroidContent")
//        webView.loadUrl("file:///android_asset/index.html")
//        webView.loadUrl("https://xcontent.dev.deepvision.team/")
        webView.loadUrl("http://ui.deepvisionserver.net/#/")
    }

    private fun setUpRv() {
        val rv = sschool_selection_tb_rv
        rv.adapter = colorAdapter
        colorAdapter.data = SSchoolHighlight.HighlightingColor.values().asList()
        rv.setHasFixedSize(true)
    }

    private fun openSelectionToolbar(selection: SSchoolHighlight) {
        main_selection_tb.visibility = View.VISIBLE
        colorAdapter.selectedColorValue = selection.color.rgb
        colorAdapter.listener = {
            if (it == SSchoolHighlight.HighlightingColor.NOT_SELECTED) {
                repo.deleteHighlighting(selection)
            } else {
                // TODO: replace delete and save with repo.updateHighlighting()
                repo.deleteHighlighting(selection)
                repo.saveHighlighting(selection.copy(color = it))
            }
            jsHighlightText(repo.getAllHighlights())
            hideSelectionToolbar()
            jsDeselectText()
        }

        sschool_selection_tb_copy_iv.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", selection.selectedText)
            clipboard.setPrimaryClip(clip)

            // Show toast "text copied to clipboard" from VM
            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
            hideSelectionToolbar()
            jsDeselectText()
        }
    }

    private fun hideSelectionToolbar() {
        runOnUiThread { main_selection_tb.visibility = View.GONE }
    }

    private fun getFakeData(): SSchoolDay {
        return SSchoolDay(
            "2020:1:1:1", "2020:1:1", "DAY 1 TITLE",
            "2020-01-01",
            "https://dv-dev.fra1.digitaloceanspaces.com/ssplus/sschool/day_cover.png",
            application.assets.open("content.json").bufferedReader().use { it.readText() },
            SSchoolDayUserData((repo.getAllHighlights())))
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        nScroll = sschool_nscroll

        webView = sschool_webview
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

    override fun onBackPressed() {
        if (main_selection_tb.visibility == View.VISIBLE) {
            hideSelectionToolbar()
            jsDeselectText()
        } else {
            super.onBackPressed()
        }
    }

}
