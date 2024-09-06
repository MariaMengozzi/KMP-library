package my.company.name.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import my.company.name.model.Person

/**
 * A class representing the Person API.
 */
class PersonAPI (engine: HttpClientEngine) {
    private val httpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json()
        }
    }

    /**
     * Retrieves all persons from the API.
     * @return A [Result] containing the list of persons or an error message.
     */
    suspend fun getPersons(): Result<List<Person>> {
        return try {
            val response: HttpResponse = httpClient.get("http://localhost:8080/persons/all") //LINK AL SERVER
            if (response.status == HttpStatusCode.OK) {
                val person = response.body<List<Person>>()
                Result.success(person)
            } else {
                Result.failure(Exception("Error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Adds a new person to the API.
     * @param person The person to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun addPerson(person: Person): Result<Person> {
        return try {
            val response: HttpResponse = httpClient.post("/persons") {
                contentType(ContentType.Application.Json)
                setBody(person)
            }
            if (response.status == HttpStatusCode.Created) {
                val body = response.body<Person>()
                Result.success(body)
            } else {
                Result.failure(Exception("Error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}