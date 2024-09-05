package my.company.name.datastore

import app.cash.sqldelight.db.SqlDriver
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class PersonDatabaseTest {
    private val db = Database(getDriver())

    @BeforeTest
    fun before() {
        db.clearDatabase()
    }

    @Test
    fun testInsertAndSelectPerson() {
        // Define a sample person
        val p1: my.company.name.model.Person = my.company.name.model.Person("123mmmi3910ilsde",
            "Mario",
            "Rossi",
            LocalDate(1990, 1, 1),
            "Roma")

        // Insert the person
        db.insertPerson(p1)

        // Retrieve all persons
        val persons = db.getAllPersons()
        println(persons)
        // Assertions
        assertEquals(1, persons.size)
        val person = persons[0]
        assertNotNull(person)
        assertEquals(p1.cf, person.cf)
    }

    @Test
    fun testInsertAndSelectNotExistingPersons() {
        // Define a sample person
        val p1: my.company.name.model.Person = my.company.name.model.Person("1223mmi3910ilsde",
            "Giuseppe",
            "Verdi",
            LocalDate(1990, 1, 1),
            "Milano")

        // Insert the person
        db.insertPerson(p1)

        // Retrieve all persons
        val persons = db.getAllPersons()
        println(persons)
        // Assertions
        assertFalse(persons.isEmpty())
        val person = persons[0]
        assertNotNull(person)
        assertNotEquals(p1.cf, "not existing cf")
    }
}

expect fun getDriver() : SqlDriver
