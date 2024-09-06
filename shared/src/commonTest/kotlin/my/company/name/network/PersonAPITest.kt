package my.company.name.network

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteReadPacket
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.readText
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import my.company.name.model.Person
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.test.KoinTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class PersonAPITest : KoinTest {

    private val apiService: PersonAPIImpl by inject()

    private val client = HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "/persons" -> {
                        assertEquals(HttpMethod.Post, request.method)
                        val persons = setOf<Person>(
                            Person("1", "John", "Doe", LocalDate.parse("2000-01-01"), "New York"),
                            Person("2", "Jane", "Smith", LocalDate.parse("1995-05-15"), "London")
                        )
                        val body = request.body
                        try {
                            val person =
                                Json.decodeFromString<Person>(body.toByteReadPacket().readText())
                            if (persons.contains(person)) {
                                throw IllegalArgumentException("Person already exists")
                            }
                            respond(
                                Json.encodeToString(person),
                                HttpStatusCode.Created,
                                headers = headersOf(HttpHeaders.ContentType, "application/json")
                            )
                        } catch (e: IllegalArgumentException) {
                            respond(e.message.orEmpty(), HttpStatusCode.BadRequest)
                        }


                    }

                    "/persons/all" -> {
                        assertEquals(HttpMethod.Get, request.method)
                        respond(
                            Json.encodeToString(
                                listOf(
                                    Person(
                                        "1",
                                        "John",
                                        "Doe",
                                        LocalDate.parse("2000-01-01"),
                                        "New York"
                                    ),
                                    Person(
                                        "2",
                                        "Jane",
                                        "Smith",
                                        LocalDate.parse("1995-05-15"),
                                        "London"
                                    )
                                )
                            ),
                            HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }

                    else -> respond("", HttpStatusCode.NotFound)
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    private val networkModule = module {
        single {
            client
        }
        single {
            PersonAPIImpl(get())
        }
    }
    @BeforeTest
    fun setUp() {
        startKoin {
            modules(networkModule)
        }
    }
    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testAddPerson() = runBlocking {
        val person = Person("3", "John", "Doe", LocalDate.parse("2000-01-01"), "New York")
        val result = apiService.addPerson(person)
        assertTrue(result.isSuccess)
        assertEquals(person.cf, result.getOrNull()?.cf)
    }

    @Test
    fun testAddPersonError() = runBlocking {
        val person = Person("1", "John", "Doe", LocalDate.parse("2000-01-01"), "New York")
        val result = apiService.addPerson(person)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.message, "Error: ${HttpStatusCode.BadRequest}")

    }

    @Test
    fun testGetAllData() = runBlocking {
        val expectedPersons = listOf(
            Person("1", "John", "Doe", LocalDate.parse("2000-01-01"), "New York"),
            Person("2", "Jane", "Smith", LocalDate.parse("1995-05-15"), "London")
        )
        val result = apiService.getPersons()
        assertTrue(result.isSuccess)
        assertEquals(expectedPersons.size, result.getOrElse { emptySet<Person>() }.size)
        assertTrue(expectedPersons.containsAll(result.getOrElse { emptyList() }))
    }

}