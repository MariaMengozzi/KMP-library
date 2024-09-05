package my.company.name.datastore

import app.cash.sqldelight.db.SqlDriver
import my.company.name.AppDatabase
import my.company.name.model.Person
import my.company.name.sqldelightconverter.toDomainModel

/** It will contain logic common to both platforms.*/
internal class Database(driver: SqlDriver) {
    private val database = AppDatabase(driver)
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllPersons() : List<Person> =
         dbQuery.selectAll().executeAsList().map { it.toDomainModel() }


    internal fun insertPerson(person: Person) =
        dbQuery.insertPerson(
            person.cf,
            person.name,
            person.surname,
            person.birthday.toString(),
            person.city
        )

    internal fun clearDatabase() =
        dbQuery.clearPerson()
}

