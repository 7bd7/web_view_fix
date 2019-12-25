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
                    Log.d("WebViewLogger", "${message()} -- From line ${lineNumber()} of ${sourceId()}")
                }
                return true
            }
        }

        nScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                _, _, scrollY, _, _ ->
            webView.evaluateJavascript("javascript: " +
                    "updateFromAndroid($scrollY)", null)
        })

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

    companion object {

        const val dayContent = "[\n" +
                "  {\n" +
                "    \"tag\":\"Heading\",\n" +
                "    \"props\":{\n" +
                "      \"text\":\"Prayer is dialogue with God?\",\n" +
                "      \"type\":\"h2\"\n" +
                "    },\n" +
                "    \"id\":\"WgKXU\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"HG002\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"tag\":\"Text\",\n" +
                "    \"props\":{\n" +
                "      \"contents\":{\n" +
                "        \"ops\":[\n" +
                "          {\n" +
                "            \"insert\":\"In communication it is important to speak and be heard. Therefore, it is necessary to approach the prayer consciously. God hears us, but we do not always hear Him. This may be due to the presence of barriers between God and people. These barriers can be overcome by conscious and constant prayer.\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"id\":\"3X2F7\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"TX001\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"tag\":\"Text\",\n" +
                "    \"props\":{\n" +
                "      \"contents\":{\n" +
                "        \"ops\":[\n" +
                "          {\n" +
                "            \"insert\":\"In communication it is important to speak and be heard. Therefore, it is necessary to approach the prayer consciously. God hears us, but we do not always hear Him. This may be due to the presence of barriers between God and people. These barriers can be overcome by conscious and constant prayer.\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"id\":\"RjLJi\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"TX001\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"tag\":\"Image\",\n" +
                "    \"props\":{\n" +
                "      \"type\":\"default\",\n" +
                "      \"image\":{\n" +
                "        \"ext\":\"jpg\",\n" +
                "        \"id\":\"RY0amrqH_\",\n" +
                "        \"mimeType\":\"image/jpeg\",\n" +
                "        \"url\":\"https://cdn.connect.dev.deepvision.team/images/bWKSmkZbW.cropped.jpg\",\n" +
                "        \"size\":26058,\n" +
                "        \"name\":\"bWKSmkZbW.cropped.jpg\",\n" +
                "        \"created\":\"2019-10-24T12:46:08.230Z\",\n" +
                "        \"props\":{\n" +
                "          \"width\":750,\n" +
                "          \"height\":320,\n" +
                "          \"parentId\":\"bWKSmkZbW\",\n" +
                "          \"parentUrl\":\"https://cdn.connect.dev.deepvision.team/images/bWKSmkZbW.jpg\",\n" +
                "          \"cropMask\":\"0,130,819,1920\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"shadow\":true,\n" +
                "      \"goals\":[\n" +
                "\n" +
                "      ]\n" +
                "    },\n" +
                "    \"id\":\"qFlhP\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"IM001\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"props\":{\n" +
                "      \"id\":\"DOkTg\",\n" +
                "      \"messages\":[\n" +
                "        {\n" +
                "          \"id\":\"kw156\",\n" +
                "          \"props\":{\n" +
                "            \"text\":\"Привет. Теперь я в твоем Android!\"\n" +
                "          },\n" +
                "          \"bot\":\"default\",\n" +
                "          \"type\":\"message\",\n" +
                "          \"meta\":{\n" +
                "            \"blockId\":\"TEXT001\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\":\"FmC1_\",\n" +
                "          \"props\":{\n" +
                "            \"text\":\"Готов ощутить всю силу системы Connect\",\n" +
                "            \"type\":\"buttons\",\n" +
                "            \"buttons\":[\n" +
                "              {\n" +
                "                \"code\":\"zqLhh\",\n" +
                "                \"text\":\"Да\",\n" +
                "                \"type\":\"primary\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"code\":\"zKn_L\",\n" +
                "                \"text\":\"process.exit()\",\n" +
                "                \"type\":\"primary\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          \"bot\":\"default\",\n" +
                "          \"type\":\"response\",\n" +
                "          \"branches\":{\n" +
                "            \"zqLhh\":[\n" +
                "              {\n" +
                "                \"id\":\"MXWUG\",\n" +
                "                \"props\":{\n" +
                "                  \"text\":\"Тогда держи фоточку\",\n" +
                "                  \"type\":\"image\",\n" +
                "                  \"attachments\":[\n" +
                "                    {\n" +
                "                      \"type\":\"image\",\n" +
                "                      \"props\":{\n" +
                "                        \"image\":{\n" +
                "                          \"ext\":\"jpg\",\n" +
                "                          \"id\":\"uCsR2pPim\",\n" +
                "                          \"mimeType\":\"image/jpeg\",\n" +
                "                          \"url\":\"https://cdn.connect.dev.deepvision.team/images/YQdLh158S.cropped.jpg\",\n" +
                "                          \"size\":26058,\n" +
                "                          \"name\":\"YQdLh158S.cropped.jpg\",\n" +
                "                          \"created\":\"2019-10-24T12:48:02.846Z\",\n" +
                "                          \"props\":{\n" +
                "                            \"width\":750,\n" +
                "                            \"height\":320,\n" +
                "                            \"parentId\":\"YQdLh158S\",\n" +
                "                            \"parentUrl\":\"https://cdn.connect.dev.deepvision.team/images/YQdLh158S.jpg\",\n" +
                "                            \"cropMask\":\"0,130,819,1920\"\n" +
                "                          }\n" +
                "                        }\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                },\n" +
                "                \"bot\":\"default\",\n" +
                "                \"type\":\"message\",\n" +
                "                \"meta\":{\n" +
                "                  \"blockId\":\"IMG001\"\n" +
                "                }\n" +
                "              }\n" +
                "            ],\n" +
                "            \"zKn_L\":[\n" +
                "\n" +
                "            ]\n" +
                "          },\n" +
                "          \"meta\":{\n" +
                "            \"blockId\":\"TEXT001\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"title\":\"\"\n" +
                "    },\n" +
                "    \"tag\":\"Dialog\",\n" +
                "    \"id\":\"DOkTg\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"DLDEFAULT\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"tag\":\"Text\",\n" +
                "    \"props\":{\n" +
                "      \"contents\":{\n" +
                "        \"ops\":[\n" +
                "          {\n" +
                "            \"insert\":\"In communication it is important to speak and be heard. Therefore, it is necessary to approach the prayer consciously. God hears us, but we do not always hear Him. This may be due to the presence of barriers between God and people. These barriers can be overcome by conscious and constant prayer.\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"id\":\"v2UiK\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"TX001\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"props\":{\n" +
                "      \"items\":[\n" +
                "        {\n" +
                "          \"ops\":[\n" +
                "            {\n" +
                "              \"insert\":\"In communication it is important to speak and be heard. Therefore, it is necessary to approach the prayer consciously.\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"ops\":[\n" +
                "            {\n" +
                "              \"insert\":\"God hears us, but we do not always hear Him.\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"ops\":[\n" +
                "            {\n" +
                "              \"insert\":\"This may be due to the presence of barriers between God and people. These barriers can be overcome by conscious and constant prayer. These barriers can be overcome by conscious and constant prayer.\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"type\":\"numbered\"\n" +
                "    },\n" +
                "    \"tag\":\"List\",\n" +
                "    \"id\":\"W9Uhl\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"LI002\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"props\":{\n" +
                "      \"items\":[\n" +
                "        {\n" +
                "          \"ops\":[\n" +
                "            {\n" +
                "              \"insert\":\"In communication it is important to speak and be heard. Therefore, it is necessary to approach the prayer consciously.\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"ops\":[\n" +
                "            {\n" +
                "              \"insert\":\"God hears us, but we do not always hear Him.\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"ops\":[\n" +
                "            {\n" +
                "              \"insert\":\"This may be due to the presence of barriers between God and people. These barriers can be overcome by conscious and constant prayer. These barriers can be overcome by conscious and constant prayer.\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"type\":\"large-numbered\"\n" +
                "    },\n" +
                "    \"tag\":\"List\",\n" +
                "    \"id\":\"1rnfm\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"LI003\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"props\":{\n" +
                "      \"color\":\"#7CCE10\",\n" +
                "      \"type\":\"shaded\",\n" +
                "      \"icon\":\"iconlib-book\",\n" +
                "      \"shadow\":true,\n" +
                "      \"subtitle\":\"Gospel of Matthew 6: 8\",\n" +
                "      \"contents\":{\n" +
                "        \"ops\":[\n" +
                "          {\n" +
                "            \"insert\":\"\\\"For your Father knows what you need, before you ask Him\\\"\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"tag\":\"Quote\",\n" +
                "    \"id\":\"jlvBF\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"QT004\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"props\":{\n" +
                "      \"title\":\"The Destruction of Jerusalem\",\n" +
                "      \"subtitle\":\"The Romans took the city, after almost five months of siege.\",\n" +
                "      \"shadow\":true,\n" +
                "      \"date\":\"70 AD\"\n" +
                "    },\n" +
                "    \"tag\":\"Event\",\n" +
                "    \"id\":\"mjj79\",\n" +
                "    \"meta\":{\n" +
                "      \"sampleId\":\"EVN001\"\n" +
                "    }\n" +
                "  }\n" +
                "]"

    }

}
