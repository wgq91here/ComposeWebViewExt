@file:OptIn(ExperimentalMaterial3Api::class)

package cn.contentflow.ktorwebview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.*
import androidx.compose.runtime.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.api.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.cache.storage.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.jvm.javaio.*
import java.util.*
import java.util.logging.Logger
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cn.contentflow.webview2.WebViewPage


var logger: Logger = Logger.getLogger("foo")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ////

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
//                    Greeting("Android")
                MainContent()


            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContent() {
    val loadURL = remember { mutableStateOf("file:///android_asset/loading.html") }

    LaunchedEffect(loadURL) {
        loadURL.value = "https://news.baidu.com"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GFG | WebView", color = Color.White) }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    loadURL.value = "https://news.sina.com.cn/?" + Random(1000)
                    logger.info("RELOAD ${loadURL.value}")
                },
                icon = {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Favorite"
                    )
                },
                text = { Text("Like") }
            )
        },
        content = {
            WebViewPage(url = loadURL.value, showProcess = true)
        }
    )

//    Scaffold(
//        topBar = {
//
//        },
//        content = {
//            WebViewPage(loadURL.value)
//            ExtendedFloatingActionButton(
//                onClick = {
//                    loadURL.value = "https://news.sina.com.cn/?" + Random(1000)
//                    logger.info("RELOAD ${loadURL.value}")
//                },
//                icon = {
//                    Icon(
//                        Icons.Filled.Favorite,
//                        contentDescription = "Favorite"
//                    )
//                },
//                text = { Text("Like") }
//            )
//        }
//    )
}

//
//// Creating a composable
//// function to display Top Bar
//@SuppressLint("CoroutineCreationDuringComposition")
//@Composable
//fun MainContent() {
//    val context = LocalContext.current
//    val loadURL = remember { mutableStateOf("file:///android_asset/loading.html") }
//    val composableScope = rememberCoroutineScope()
//
//    LaunchedEffect(loadURL) {
//        if (isOnline(context)) {
//            loadURL.value = "https://news.sina.com.cn/"
////            composableScope.launch {
////                val cacheLocal = loadUrlContent(loadURL.value, context)
////                logger.info("loadUrlContent = $cacheLocal!")
////                loadURL.value = cacheLocal
////
//////                val cacheLocal = loadUrlContentByHttpCache(loadURL.value, context)
////                val cacheFile = File(cacheLocal)
////                cacheFile.listFiles()?.forEach { f ->
////                    logger.info("cacheLocal-> ${f.name}")
////                    logger.info("context->${f.readText()}")
////                }
////            }
//        } else {
//            loadURL.value = "file:///android_asset/404.html"
//        }
////        loadURL.value = "https://news.baidu.com"
//    }
////     if (isOnline(context)) {
////        loadURL //"file:///android_asset/404.html" // general error message
////    } else {
////        "file:///android_asset/error.html" // no internet connection
////    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("GFG | WebView", color = Color.White) },
//                backgroundColor = Color(0xff0f9d58)
//            )
//        },
//        content = {
//            WebViewPage(loadURL.value)
//            ExtendedFloatingActionButton(
//                onClick = {
//                    loadURL.value = "https://news.sina.com.cn/?" + Random(1000)
//                    logger.info("RELOAD ${loadURL.value}")
//                },
//                icon = {
//                    Icon(
//                        Icons.Filled.Favorite,
//                        contentDescription = "Favorite"
//                    )
//                },
//                text = { Text("Like") }
//            )
//        }
//    )
//}
//
//// Creating a composable
//// function to create WebView
//// Calling this function as
//// content in the above function
//
//@SuppressLint("SetJavaScriptEnabled")
//@Composable
//fun WebViewPage(url: String) {
////    val mutableStateTrigger = remember { mutableStateOf(false) }
//    val context = LocalContext.current
//    AndroidView(
//        factory = {
//            WebView(it).apply {
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
////                webViewClient = WebViewClient()
//                webViewClient = getClient(context)
//
//                // to play video on a web view
//                settings.javaScriptEnabled = true
//                settings.allowContentAccess = true
//                settings.allowFileAccess = true
////                webViewClient = object : WebViewClient() {
////                    override fun onReceivedError(
////                        view: WebView?,
////                        request: WebResourceRequest?,
////                        error: WebResourceError?
////                    ) {
////                        super.onReceivedError(view, request, error)
////                        loadURL = if (isOnline(context)) {
////                            loadURL //"file:///android_asset/404.html" // general error message
////                        } else {
////                            "file:///android_asset/error.html" // no internet connection
////                        }
////                        mutableStateTrigger.value = true
////                    }
////                }
//                loadUrl(url)
//            }
//        }, update = {
//            it.loadUrl(url)
//        })
//
//
////    if (mutableStateTrigger.value) {
////        WebViewPage(loadURL)
////    }
//}
//
//// For displaying preview in
//// the Android Studio IDE emulator
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MainContent()
//}
//
////fun getBitmapInputStream(bitmap: Bitmap, compressFormat: Bitmap.CompressFormat): InputStream {
////    val byteArrayOutputStream = ByteArrayOutputStream()
////    bitmap.compress(compressFormat, 80, byteArrayOutputStream)
////    val bitmapData: ByteArray = byteArrayOutputStream.toByteArray()
////    return ByteArrayInputStream(bitmapData)
////}
//
//val webCache = Cache.Builder().maximumCacheSize(50).build<String, WebResourceResponse>()
//
////class StreamContent(private val binaryFile: File) : OutgoingContent.WriteChannelContent() {
////    override suspend fun writeTo(channel: ByteWriteChannel) {
////        binaryFile.inputStream().copyTo(channel.toOutputStream(), 1024)
////    }
////
////    //    override val contentType = ContentType.Application.Pdf
////    override val contentLength: Long = binaryFile.length()
////}
//
////fun convertCachedFileToStream(url: Uri): InputStream {
////    val client = HttpClient(CIO)
//////    val inputFile = InputStream
////    val file = File(url.pathSegments.last())
////    runBlocking {
////        url.path?.let { client.get(it).bodyAsChannel().copyAndClose(file.writeChannel()) }
////    }
////    return file.inputStream()
////}
//
//fun getClient(context: Context): WebViewClient {
//    return object : WebViewClient() {
////        override fun shouldInterceptRequest(
////            view: WebView?,
////            request: WebResourceRequest?
////        ): WebResourceResponse? {
////            return super.shouldInterceptRequest(view, request)
////        }
//
//        override fun shouldInterceptRequest(
//            view: WebView?,
//            request: WebResourceRequest
//        ): WebResourceResponse? {
//
//            // Only cache GET calls
//            if (!"GET".equals(request.method, ignoreCase = true)) {
//                return super.shouldInterceptRequest(view, request)
//            }
//
//            // Init the cache config, fetch from network if not done already
////            val urls: List<String?> = initCacheConfig()
//
//            // Check if we should cache this path
////            if (!urls.contains(request.url.path)) {
////                return super.shouldInterceptRequest(view, request)
////            }
//            val k = request.url.toString()
//            val resourceResponse: WebResourceResponse? = webCache.get(k)
//            if (resourceResponse != null) {
//                logger.info("No Cache -> $k")
//                return resourceResponse
//            } else {
//                runBlocking {
//                    val c = getContentAndStore(request)
//                    if (c != null) {
//                        logger.info("Use Cache -> $k")
//                        webCache.put(k, c)
//                    }
//                }
//            }
//            return super.shouldInterceptRequest(view, request)
//        }
//
//        private suspend fun getContentAndStore(request: WebResourceRequest): WebResourceResponse? {
////            val url = request.url.path
//            logger.info("getContentAndStore ${request.url}")
//            var webResourceResponse: WebResourceResponse? = null
//
//            request.url.let {
//                try {
////                if (!contentAvailable(url)) {
////                    downloadUrlContent(url)
////                    updateCacheTTL(url)
////                }
////                val resourceStream: InputStream = convertCachedFileToStream(request.url)
//                    val client = HttpClient(CIO) {
//                        install(Logging) {
//                            level = LogLevel.ALL
//                        }
//                    }
//                    val httpResponse: HttpResponse = client.get(it.toString()) {
//                        onDownload { bytesSentTotal, contentLength ->
//                            println("Received $bytesSentTotal bytes from $contentLength")
//                        }
//                    }
//                    val responseBody: ByteArray = httpResponse.body()
////                    logger.info("webResourceResponse! ${httpResponse.headers.toMap()}")
//
//                    val contentType = httpResponse.headers["Content-Type"] ?: ""
//                    val mimeType =
//                        "([-\\w.]+/[-\\w.]+)".toRegex().find(contentType).let { mr ->
//                            logger.info("toRegex ${mr?.value} $contentType")
//                            mr?.value ?: "text/plain"
//                        }
//                    /// No Cache Video | Audio
////                    if (mimeType.contains("video", true) || mimeType.contains("audio", true)) {
////                        return@let null
////                    }
//                    /// 只缓存图片
//                    if (mimeType.contains("image", true) || mimeType.contains("html", true)) {
//                        logger.info("toRegex CACHE -> $it  $mimeType")
//                        webResourceResponse = WebResourceResponse(
////                    mimeType,
//                            mimeType,
//                            "utf-8",
//                            200,
//                            "OK",
////                    responseHeaders,
//                            request.requestHeaders,
////                        resourceStream
//                            responseBody.inputStream()
//                        )
//                        logger.info("webResourceResponse! $mimeType")
//                    }
//                } catch (e: Exception) {
//                    // Log error
//                    logger.info("Exception! ${e.message}")
//                }
//            }
//
//            return webResourceResponse
//        }
//
////        @Deprecated("Deprecated in Java")
////        override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
////            if (url == null) {
////                return super.shouldInterceptRequest(view, url as String)
////            }
////            logger.info("shouldInterceptRequest -> $url")
////            return if (url.toLowerCase(Locale.ROOT).contains(".jpg") || url.toLowerCase(Locale.ROOT)
////                    .contains(".jpeg")
////            ) {
////                val bitmap =
////                    Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
////                        .load(url).submit().get()
////                WebResourceResponse(
////                    "image/jpg",
////                    "UTF-8",
////                    getBitmapInputStream(bitmap, Bitmap.CompressFormat.JPEG)
////                )
////            } else if (url.toLowerCase(Locale.ROOT).contains(".png")) {
////                val bitmap =
////                    Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
////                        .load(url).submit().get()
////                WebResourceResponse(
////                    "image/png",
////                    "UTF-8",
////                    getBitmapInputStream(bitmap, Bitmap.CompressFormat.PNG)
////                )
////            }
//////            else if (url.toLowerCase(Locale.ROOT).contains(".webp")) {
//////                val bitmap = Glide.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
//////                    .load(url).submit().get()
//////                WebResourceResponse(
//////                    "image/webp",
//////                    "UTF-8",
//////                    getBitmapInputStream(bitmap, Bitmap.CompressFormat)
//////                )
//////            }
////            else {
////                super.shouldInterceptRequest(view, url)
////            }
////
////        }
//    }
//}
////val AutoHttpCachePlugin = createClientPlugin("AutoHttpCachePlugin") {
////    onRequest { request, _ ->
////        logger.in
////    }
////}
//
//suspend fun loadUrlContentByHttpCache(url: String, context: Context): String {
////    val cacheFile = File(context.filesDir, "index.html")
//    val cacheFile = context.cacheDir.path + "/webCache"
//    val cache = File(cacheFile)
//    cache.delete()
//
//    val client = HttpClient(CIO) {
//        install(HttpCache) {
//            publicStorage(FileStorage(cache))
//        }
//        install(Logging) {
//            level = LogLevel.ALL
//        }
//    }
//    client.prepareGet(url).execute { _ ->
//        logger.info("loadUrlContentByHttpCache A file saved to ${cache.path}")
//    }
//    return cache.path
//}
//
//
//suspend fun loadUrlContent(url: String, context: Context): String {
//    val client = HttpClient(CIO)
//    val cacheFile = context.cacheDir.path + "/webCache.html"
//    val cache = File(cacheFile)
//    cache.delete()
//
//    runBlocking {
//        client.prepareGet(url).execute { httpResponse ->
//            val channel: ByteReadChannel = httpResponse.body()
//            while (!channel.isClosedForRead) {
//                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
//                while (!packet.isEmpty) {
//                    val bytes = packet.readBytes()
//                    cache.appendBytes(bytes)
//                    logger.info(
//                        "Received ${cache.length()} bytes from ${httpResponse.contentLength()}"
//                    )
//                }
//            }
//            logger.info("A file saved to ${cache.path}")
//        }
//    }
//    return cache.path
//}
//
//fun isOnline(context: Context): Boolean {
//    val connectivityManager =
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//    // For 29 api or above
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//                ?: return false
//        return when {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            else -> false
//        }
//    }
//    // For below 29 api
//    else {
//        @Suppress("DEPRECATION")
//        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
//            return true
//        }
//    }
//    return false
//}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    KtorWebViewTheme {
//        Greeting("Android")
//    }
//}