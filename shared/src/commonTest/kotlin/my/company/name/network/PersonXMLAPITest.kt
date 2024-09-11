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
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import kotlinx.serialization.builtins.ListSerializer
import my.company.name.model.entity.PersonXML
import my.company.name.serializer.NetworkXMLSerializer
import nl.adaptivity.xmlutil.serialization.XML
import org.koin.test.KoinTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class PersonXMLAPITest : KoinTest {

    private val apiService: PersonXMLAPIImpl by inject()

    private val client = HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "/persons" -> {
                        assertEquals(HttpMethod.Post, request.method)
                        val persons : Set<PersonXML> = setOf(
                            PersonXML("1", "John", "Doe", LocalDate.parse("2000-01-01").toString(), "New York"),
                            PersonXML("2", "Jane", "Smith", LocalDate.parse("1995-05-15").toString(), "London")
                        )
                        val body = request.body
                        try {
                            val person =
                                XML.decodeFromString<PersonXML>(body.toByteReadPacket().readText())
                            if (persons.contains(person)) {
                                throw IllegalArgumentException("Person already exists")
                            }
                            respond(
                                XML.encodeToString(person),
                                HttpStatusCode.Created,
                                headers = headersOf(HttpHeaders.ContentType, "application/xml")
                            )
                        } catch (e: IllegalArgumentException) {
                            respond(e.message.orEmpty(), HttpStatusCode.BadRequest)
                        }


                    }

                    "/persons/all" -> {
                        assertEquals(HttpMethod.Get, request.method)
                        val persons : List<PersonXML> = listOf(
                            PersonXML(
                                "1",
                                "John",
                                "Doe",
                                LocalDate.parse("2000-01-01").toString(),
                                "New York"
                            ),
                            PersonXML(
                                "2",
                                "Jane",
                                "Smith",
                                LocalDate.parse("1995-05-15").toString(),
                                "London"
                            )
                        )

                        respond(
                            XML.encodeToString(ListSerializer(PersonXML.serializer()), persons),
                            HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/xml")
                        )
                    }

                    else -> respond("", HttpStatusCode.NotFound)
                }
            }
        }
        install(ContentNegotiation) {
            register(ContentType.Application.Xml, NetworkXMLSerializer())
        }
    }

    private val networkModule = module {
        single {
            client
        }
        single {
            PersonXMLAPIImpl()
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
        val person = PersonXML("3", "John", "Doe", LocalDate.parse("2000-01-01").toString(), "New York")
        val result = apiService.addPerson(person)
        println(result)
        assertTrue(result.isSuccess)
        assertEquals(person.cf, result.getOrNull()?.cf)
    }

    @Test
    fun testAddPersonError() = runBlocking {
        val person = PersonXML("1", "John", "Doe", LocalDate.parse("2000-01-01").toString(), "New York")
        val result = apiService.addPerson(person)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.message, "Error: ${HttpStatusCode.BadRequest}")

    }

    @Test
    fun testGetAllData() = runBlocking {
        val expectedPersons = listOf(
            PersonXML("1", "John", "Doe", LocalDate.parse("2000-01-01").toString(), "New York"),
            PersonXML("2", "Jane", "Smith", LocalDate.parse("1995-05-15").toString(), "London")
        )
        val result = apiService.getPersons()
        println(result)
        assertTrue(result.isSuccess)
        assertEquals(expectedPersons.size, result.getOrElse { emptySet() }.size)
        assertTrue(expectedPersons.containsAll(result.getOrElse { emptyList() }))
    }

}