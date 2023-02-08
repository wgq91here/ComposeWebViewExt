package cn.contentflow.webview2
//
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//import io.ktor.client.*
//import io.ktor.client.engine.android.*
//import io.ktor.client.engine.cio.*
//import javax.inject.Singleton
//import io.ktor.client.plugins.logging.*
//import org.tinylog.kotlin.Logger as KLog
//
//
//open class KHttpClient: KHttpClient() {
//
//}
//
//

//@HiltAndroidApp
//class BaseApplication: Application()

//@Module
//@InstallIn(SingletonComponent::class)
//object ApiModule {
//
//    @Provides
//    @Singleton
//    fun provideApplication(@ApplicationContext context: Context): Context {
//        return context
//    }
//
//}
//    @Provides
//    @Singleton
//    fun provideHttpClient(): HttpClient {
//        return HttpClient(CIO) {
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        KLog.info("HTTP Client", message)
//                    }
//                }
////                level = LogLevel.HEADERS
////                filter { request ->
////                    request.url.host.contains("ktor.io")
////                }
//            }
//            engine {
////             this: CIOEngineConfig
//                maxConnectionsCount = 100
//                endpoint {
//                    // this: EndpointConfig
//                    maxConnectionsPerRoute = 10
//                    pipelineMaxSize = 20
//                    keepAliveTime = 3000
//                    connectTimeout = 3000
//                    connectAttempts = 5
//                }
//            }
//        }
//    }