package my.company.name.datastore

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import kotlinx.datetime.LocalDate
import my.company.name.model.entity.PersonRealm
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import kotlin.test.assertEquals

class PersonRealmDatabaseTest : KoinTest {

    private val realmModule = module {
        single {
            val config = RealmConfiguration.Builder(schema = setOf(PersonRealm::class))
                .inMemory() // Database in-memory per i test
                .name("test-realm")
                .build()

            Realm.open(config)
        }
    }

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(realmModule)
        }
    }


    @Test
    fun testInsertAndQuery() {
        val p1 : PersonRealm = PersonRealm()
        p1.apply {
            cf = "1"
            name = "John"
            surname = "Doe"
            birthday = RealmInstant.from(LocalDate.parse("2000-01-01").toEpochDays().toLong(), 0)
            city = "Rome"
        }


        PersonRealmQuery.storePerson(p1)

        val result = PersonRealmQuery.getAllPersons().firstOrNull()

        assertEquals(p1, result)
    }

}