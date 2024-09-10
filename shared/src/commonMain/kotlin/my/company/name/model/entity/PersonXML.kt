package my.company.name.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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
