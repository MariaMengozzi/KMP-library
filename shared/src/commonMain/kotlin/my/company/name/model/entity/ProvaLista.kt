package my.company.name.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlChildrenName
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@SerialName("prova")
@XmlSerialName("prova")
data class ProvaLista(
    @XmlChildrenName("id")
    @SerialName("id")
    val id: String,
    @SerialName("person")
    @XmlSerialName("persone")
    val persona: List<PersonXML>
)
