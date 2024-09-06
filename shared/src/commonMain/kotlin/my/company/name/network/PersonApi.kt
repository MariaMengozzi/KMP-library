package my.company.name.network

import my.company.name.model.Person

/**
 * An interface representing the Person API.
 */
interface PersonApi {
    /**
     * Retrieves all persons from the API.
     * @return A [Result] containing the list of persons or an error message.
     * */
    suspend fun getPersons(): Result<List<Person>>
    /**
     * Adds a new person to the API.
     * @param person The person to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun addPerson(person: Person): Result<Person>

}