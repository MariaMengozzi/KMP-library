package my.company.name.datastore

import io.realm.kotlin.Realm
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RealmDatabase : KoinComponent {

    private val realm: Realm by inject()

    fun closeDatabase() {
        realm.close()
    }

    fun provideDatabase(): Realm {
        return realm
    }
}