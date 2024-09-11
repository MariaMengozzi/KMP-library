package my.company.name.serializer

import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.serialization.ContentConverter
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.readRemaining
import io.ktor.utils.io.readText
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer
import my.company.name.model.entity.PersonXML
import my.company.name.model.entity.XMLSerializer
import nl.adaptivity.xmlutil.serialization.XML
import kotlin.reflect.KClass

class NetworkXMLSerializer(private val xml: XML = XML()) : ContentConverter {
    override suspend fun deserialize(
        charset: Charset,
        typeInfo: TypeInfo,
        content: ByteReadChannel
    ): Any {
        val text = content.readRemaining().readText()

        return if (typeInfo.kotlinType?.classifier == List::class) {
            // Handle deserialization of a list
            XMLSerializer.xmlToPersonsList(text)
        } else {
            // Handle deserialization of a single object
            XMLSerializer.xmlToPerson(text)
        }
    }

    override suspend fun serialize(
        contentType: ContentType,
        charset: Charset,
        typeInfo: TypeInfo,
        value: Any?
    ): OutgoingContent {
        var xmlString = ""
        if (typeInfo.kotlinType?.classifier == List::class) {
            // Handle serialization of a list
             xmlString = XMLSerializer.personsListToXML(value as List<PersonXML>)
        } else {
            // Handle serialization of a single object
            xmlString = XMLSerializer.personToXML(value as PersonXML)
        }
        return TextContent(xmlString, contentType.withCharset(charset))

    }


}