package my.company.name.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteReadPacket
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.readText
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import my.company.name.model.Person
import kotlin.test.assertEquals

class PersonAPITest {

    private val client = HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "/persons" -> {
                        assertEquals(HttpMethod.Post, request.method)
                        val body = request.body
                        val person = Json.decodeFromString<Person>(body.toByteReadPacket().readText())
                        respond(Json.encodeToString(person), HttpStatusCode.Created)
                    }
                    "/persons/all" -> {
                        assertEquals(HttpMethod.Get, request.method)
                        respond(Json.encodeToString(listOf(
                            Person("1", "John", "Doe", LocalDate.parse("2000-01-01"), "New York"),
                            Person("2", "Jane", "Smith", LocalDate.parse("1995-05-15"), "London")
                        )), HttpStatusCode.OK)
                    }
                    else -> respond("", HttpStatusCode.NotFound)
                }
            }
        }
    }

    private val apiService = PersonAPI(client)

    /*@Test
    fun testSendData() = runBlocking {
        val person = Person("1", "John", "Doe", "2000-01-01", "New York")
        val result = apiService.sendData(person)

        assertTrue(result.isSuccess)
        assertEquals(HttpStatusCode.Created, result.getOrNull()?.status)
    }


    @Test
    fun testGetPersonError() = runBlocking {
        val errorClient = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    respond("", HttpStatusCode.NotFound)
                }
            }
        }
        val errorApiService = ApiService(errorClient)

        val result = errorApiService.getPerson("1")
        assertTrue(result.isFailure)
        assertEquals("Error: 404", result.exceptionOrNull()?.message)
    }*/
}