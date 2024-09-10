package my.company.name.datastore

import io.realm.kotlin.Realm
import my.company.name.datastore.RealmDatabase.provideDatabase
import my.company.name.model.entity.PersonRealm

internal object PersonRealmQuery {
    private val realm: Realm = provideDatabase()

    fun storePerson (person: PersonRealm) {
        realm.writeBlocking {
            copyToRealm(person)
        }
    }

    fun getAllPersons(): List<PersonRealm> {
        return realm.query(PersonRealm::class).find()
    }
}


