package my.company.name.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import my.company.name.model.Person
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * A class representing the Person API.
 */
class PersonAPIImpl (private val httpClient: HttpClient): PersonApi {

    //private val httpClient: HttpClient by inject() con koincomponent

    override suspend fun getPersons(): Result<List<Person>> {
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

    override suspend fun addPerson(person: Person): Result<Person> {
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