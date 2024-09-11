package my.company.name.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlChildrenName
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@SerialName("person")
@XmlSerialName(value = "person")
data class PersonXML(
    @XmlChildrenName("cf")
    val cf: String,
    @XmlChildrenName("name")
    val name: String,
    @XmlChildrenName("surname")
    val surname: String,
    @XmlChildrenName("birthday")
    val birthday: String,
    @XmlChildrenName("city")
    val city: String
)

internal object XMLSerializer {
    fun personToXML(person: PersonXML): String {
        return XML.encodeToString(person)
    }

    fun xmlToPerson(xml: String): PersonXML {
        return XML.decodeFromString(xml)
    }

    fun personsListToXML(personsList: List<PersonXML>): String {
        return XML.encodeToString(ListSerializer(PersonXML.serializer()), personsList)
    }

    fun xmlToPersonsList(xml: String): List<PersonXML> {
        return XML.decodeFromString(ListSerializer(PersonXML.serializer()), xml)
    }
}
