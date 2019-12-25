package team.deepvision.webviewfix

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var nScroll: NestedScrollView
    private lateinit var jsInterface: AndroidContent

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.sschool_webview)
        nScroll = findViewById(R.id.sschool_nscroll)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webChromeClient = object : WebChromeClient() {

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.apply {
                    Log.d(
                        "WebViewLogger",
                        "${message()} -- From line ${lineNumber()} of ${sourceId()}"
                    )
                }
                return true
            }
        }

        nScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            webView.evaluateJavascript(
                "javascript: " +
                        "updateFromAndroid($scrollY)", null
            )
        })

        val fileName = "content.json"
        val dayContent = application.assets.open(fileName).bufferedReader().use { it.readText() }
        setDayContent(dayContent)
    }

    private fun setDayContent(content: String) {
        jsInterface = AndroidContent(content)
        webView.addJavascriptInterface(jsInterface, "AndroidContent")
        webView.loadUrl("file:///android_asset/index.html")
    }

    inner class AndroidContent(private val content: String) {

        @JavascriptInterface
        fun get() = content

    }

}
