package my.company.name.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import my.company.name.model.entity.PersonRealm
import my.company.name.network.PersonAPIImpl
import my.company.name.network.PersonApi
import my.company.name.serializer.NetworkXMLSerializer
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
    modules(
        networkModule,
        realmModule
    )
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
                register(ContentType.Application.Xml, NetworkXMLSerializer()) //custom xml serializer
            }
            /*install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }*/
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

private val realmModule = module {
    single {
        val config = RealmConfiguration.Builder(schema = setOf(PersonRealm::class))
            .name("person-realm")
            .build()

        Realm.open(config)
    }
}