package my.company.name.datastore

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.RealmInstant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import my.company.name.model.entity.PersonRealm
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mongodb.kbson.BsonObjectId
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PersonRealmDatabaseTest : KoinTest {
    private var id: BsonObjectId = BsonObjectId()

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

    @AfterTest
    fun tearDown() {
        PersonRealmQuery.deletePerson(id)
        stopKoin()
    }

    @Test
    fun testInsertAndQuery() {
        val p1 = PersonRealm()
        p1.apply {
            _id = BsonObjectId()
            cf = "1234567890123"
            name = "John"
            surname = "Doe"
            birthday = RealmInstant.from(LocalDate.parse("2000-01-01").toEpochDays().toLong(), 0)
            city = "Rome"
        }
        id = p1._id
        PersonRealmQuery.storePerson(p1)

        val result = PersonRealmQuery.getAllPersons().firstOrNull()
        assertEquals(p1._id, result?._id)


    }

}