package my.company.name.datastore

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import my.company.name.datastore.RealmDatabase.provideDatabase
import my.company.name.model.entity.PersonRealm
import org.mongodb.kbson.ObjectId

internal object PersonRealmQuery {
    private val realm: Realm = provideDatabase()

    fun storePerson (person: PersonRealm) {
        realm.writeBlocking {
            copyToRealm(person /*, UpdatePolicy.ALL*/)
        }
    }

    fun getAllPersons(): List<PersonRealm> {
        return realm.query(PersonRealm::class).find()
    }

    fun deletePerson(id: ObjectId) {
        val frozenPerson = realm.query<PersonRealm>("_id == $0", id).find().firstOrNull()

        // Open a write transaction
        realm.writeBlocking {
            // Get the live frog object with findLatest(), then delete it
            if (frozenPerson != null) {
                findLatest(frozenPerson)
                    ?.also { delete(it) }
            }
        }
    }
}


