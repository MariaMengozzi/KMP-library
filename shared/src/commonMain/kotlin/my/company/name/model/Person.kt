package my.company.name.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import my.company.name.serializer.LocalDateSerializer

@Serializable
data class Person(
    @SerialName("cf")
    val cf: String,
    @SerialName("name")
    val name:String,
    @SerialName("surname")
    val surname: String,
    @SerialName("birthday")
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate,
    @SerialName("city")
    val city: String
)
