package my.company.name.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import my.company.name.network.PersonAPIImpl
import my.company.name.network.PersonApi
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.math.sin

/**
 * Initializes Koin, the dependency injection framework.
 *
 * This function configures Koin with the `networkModule`, which provides
 * the HTTP client for making network requests.
 */
fun initKoin() = startKoin {
    modules(networkModule)
}

/**
 * Koin module providing the HTTP client for making network requests.
 */
private val networkModule = module {
    single {
        HttpClient {
            defaultRequest {
                url.takeFrom(URLBuilder().takeFrom("https://myserverDomain.com/")) //TODO change with URL of the server
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            /*install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }*/
        }
    }

    single {
        PersonAPIImpl(get())
    }

}