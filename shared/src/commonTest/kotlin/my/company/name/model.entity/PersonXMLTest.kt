package my.company.name.model.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertEquals

class PersonXMLTest {
    @Test
    fun testPersonXMLSerialization() {
        val person = PersonXML(
            cf = "1234567890123",
            name = "John",
            surname = "Doe",
            birthday = LocalDate.parse("2000-01-01").toString(),
            city = "Rome"
        )
        val encoded = XML.encodeToString(person)

        val output = "<person><cf>1234567890123</cf><name>John</name><surname>Doe</surname><birthday>2000-01-01</birthday><city>Rome</city></person>"
        assertEquals(encoded, output)
    }

    @Test
    fun testPersonXMLDeserialization() {
        val input = "<person><cf>1234567890123</cf><name>John</name><surname>Doe</surname><birthday>2000-01-01</birthday><city>Rome</city></person>"
        val person = PersonXML(
            cf = "1234567890123",
            name = "John",
            surname = "Doe",
            birthday = LocalDate.parse("2000-01-01").toString(),
            city = "Rome"
        )
        val decoded : PersonXML = XML.decodeFromString(input)

        val output = "<person><cf>1234567890123</cf><name>John</name><surname>Doe</surname><birthday>2000-01-01</birthday><city>Rome</city></person>"
        assertEquals(decoded, person)
    }

}