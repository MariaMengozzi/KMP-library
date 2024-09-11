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
import my.company.name.model.entity.PersonXML
import my.company.name.model.entity.XMLSerializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * A class representing the Person API.
 */
class PersonXMLAPIImpl : PersonXMLApi, KoinComponent {

    private val httpClient: HttpClient by inject()

    override suspend fun getPersons(): Result<List<PersonXML>> {
        return try {
            val response: HttpResponse = httpClient.get("http://localhost:8080/persons/all") //LINK AL SERVER
            if (response.status == HttpStatusCode.OK) {
                val person = response.body<List<PersonXML>>()
                Result.success(person)
            } else {
                Result.failure(Exception("Error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addPerson(person: PersonXML): Result<PersonXML> {
        return try {
            val response: HttpResponse = httpClient.post("/persons") {
                contentType(ContentType.Application.Xml)
                setBody(XMLSerializer.personToXML(person))
            }
            if (response.status == HttpStatusCode.Created) {
                val body = response.body<PersonXML>()
                Result.success(body)
            } else {
                Result.failure(Exception("Error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}