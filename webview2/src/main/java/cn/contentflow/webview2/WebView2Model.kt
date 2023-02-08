package cn.contentflow.webview2

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.tinylog.kotlin.Logger

class WebView2Model : ViewModel() {

    var currentProgress by mutableStateOf(0.00F)
    var isOnline by mutableStateOf(false)

    fun checkOnline(context: Context) {
        isOnline = isOnline(context)
        Logger.info("checkOnline $isOnline")
    }

    fun getWebViewClient(): WebViewClient {
        return object : WebViewClient() {
//        override fun shouldInterceptRequest(
//            view: WebView?,
//            request: WebResourceRequest?
//        ): WebResourceResponse? {
//            return super.shouldInterceptRequest(view, request)
//        }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest
            ): WebResourceResponse? {

                // Only cache GET calls
                if (!"GET".equals(request.method, ignoreCase = true)) {
                    return super.shouldInterceptRequest(view, request)
                }

                // Init the cache config, fetch from network if not done already
//            val urls: List<String?> = initCacheConfig()

                // Check if we should cache this path
//            if (!urls.contains(request.url.path)) {
//                return super.shouldInterceptRequest(view, request)
//            }
                val k = request.url.toString()
                val resourceResponse: WebResourceResponse? = webCache.get(k)
                if (resourceResponse != null) {
                    Logger.info("No Cache -> $k")
                    return resourceResponse
                } else {
                    runBlocking {
                        val c = getContentAndStore(request)
                        if (c != null) {
                            Logger.info("Use Cache -> $k")
                            webCache.put(k, c)
                        }
                    }
                }
                return super.shouldInterceptRequest(view, request)
            }

            private suspend fun getContentAndStore(request: WebResourceRequest): WebResourceResponse? {
                Logger.info("getContentAndStore ${request.url}")
                var webResourceResponse: WebResourceResponse? = null

                request.url.let {
                    try {
                        val client = HttpClient(CIO) {
//                            install(Logging) {
//                                level = LogLevel.ALL
//                            }
                        }
//                        val df = DecimalFormat("#.##")
                        val httpResponse: HttpResponse = client.get(it.toString()) {
//                            var currentLength = 0L
                            onDownload { bytesSentTotal, contentLength ->
//                                currentProgress = 0.5F
                                if (contentLength > 0)
                                    currentProgress =
                                        (bytesSentTotal.toDouble() / contentLength).toFloat()
                                println(
                                    "${it.path} Received $bytesSentTotal bytes from $contentLength ${currentProgress}%"
                                )
                            }
                        }
                        val responseBody: ByteArray = httpResponse.body()

                        val contentType = httpResponse.headers["Content-Type"] ?: ""
                        val mimeType =
                            "([-\\w.]+/[-\\w.]+)".toRegex().find(contentType).let { mr ->
                                Logger.info("toRegex ${mr?.value} $contentType")
                                mr?.value ?: "text/plain"
                            }
                        /// No Cache Video | Audio
//                    if (mimeType.contains("video", true) || mimeType.contains("audio", true)) {
//                        return@let null
//                    }
                        /// 只缓存图片
                        if (mimeType.contains("image", true) || mimeType.contains("html", true)) {
                            Logger.info("toRegex CACHE -> $it  $mimeType")
                            webResourceResponse = WebResourceResponse(
//                    mimeType,
                                mimeType,
                                "utf-8",
                                200,
                                "OK",
//                    responseHeaders,
                                request.requestHeaders,
//                        resourceStream
                                responseBody.inputStream()
                            )
                            Logger.info("webResourceResponse! $mimeType")
                        }
                    } catch (e: Exception) {
                        // Log error
                        Logger.info("Exception! ${e.message}")
                    }
                }

                return webResourceResponse
            }

        }
    }
}

@SuppressLint("ServiceCast")
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // For 29 api or above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
    // For below 29 api
    else {
        @Suppress("DEPRECATION")
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}