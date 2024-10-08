package my.company.name.model.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId

class PersonRealm : RealmObject {
    @PrimaryKey
    var _id: BsonObjectId = BsonObjectId()
    var cf : String = ""
    var name : String = ""
    var surname : String = ""
    var birthday : RealmInstant? = null //store time information as a Unix epoch timestamp.
    var city : String? = ""
}