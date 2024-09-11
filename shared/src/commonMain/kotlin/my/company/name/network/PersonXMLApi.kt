package my.company.name.network

import my.company.name.model.entity.PersonXML

/**
 * An interface representing the Person API.
 */
interface PersonXMLApi {
    /**
     * Retrieves all persons from the API.
     * @return A [Result] containing the list of persons or an error message.
     * */
    suspend fun getPersons(): Result<List<PersonXML>>
    /**
     * Adds a new person to the API.
     * @param person The person to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun addPerson(person: PersonXML): Result<PersonXML>

}