package cn.contentflow.webview2

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.reactivecircus.cache4k.Cache
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.tinylog.kotlin.*

val webCache = Cache.Builder().maximumCacheSize(50).build<String, WebResourceResponse>()

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(
    url: String,
    modifier: Modifier = Modifier,
    showProcess: Boolean = true
) {
//    val mutableStateTrigger = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val webView2Model = viewModel<WebView2Model>()
//    val currentProgress = remember { rememberUpdatedState(webView2Model.currentProgress) }
    val currentProgress by rememberUpdatedState(newValue = webView2Model.currentProgress)
    val isOnline by rememberUpdatedState(newValue = webView2Model.isOnline)
    val currentUrl = remember { mutableStateOf("file:///android_asset/loading.html") }

    ////
    LaunchedEffect(url) {
        webView2Model.checkOnline(context = context)
        if (!isOnline) {
            currentUrl.value = "file:///android_asset/404.html"
        } else currentUrl.value = url
    }
    ///
    ///
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = webView2Model.getWebViewClient()
                    // to play video on a web view
                    settings.javaScriptEnabled = true
                    settings.allowContentAccess = true
                    settings.allowFileAccess = true
                    //
                    loadUrl(currentUrl.value)
                }
            }, update = {
                it.loadUrl(currentUrl.value)
            })
        if (showProcess)
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95F)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 5.dp)
                    .height(3.dp),
            ) {
                LinearProgressIndicator(
                    progress = currentProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    trackColor = Color.LightGray,
                    color = Color.Green.copy(alpha = 0.8F)
                )
            }
    }

//    if (mutableStateTrigger.value) {
//        WebViewPage(loadURL)
//    }
}

