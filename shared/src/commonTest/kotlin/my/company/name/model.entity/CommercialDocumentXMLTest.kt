package my.company.name.model.entity

import kotlinx.datetime.LocalDateTime
import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertEquals

class CommercialDocumentXMLTest {
    val dataConfig = ConfigurationDCDataXML(
        "DSW10",
        "hashJournalPrec",
        "hashElemJournalPrec",
        "MatricolaPEM"
    )

    val merchant = MerchantXML(
        "11359591002",
        "DNTCRL65S67M126L",
        RegistryXML(
            name = "Mario",
            surname = "Rossi",
            site = SiteXML(
                "Via Roma",
                "123",
                "00100",
                "Roma",
                "RM",
                "Italia",
                "0"
            )
        )
    )

    val cd = CommercialDocumentDataXML(
        clientCf = "IT12345678901",
        dateTime = LocalDateTime.parse("2024-09-16T15:30:10"),
        progressiveNumber = "000123",
        summary = listOf(
            SummaryXML(
                iva = IvaXML(vatRate = 22.0f, totalVatTax = 100.0f),
                totalAmount = 200.0f,
                taxableAmount = 150.0f,
                uncollectedAssignmentsGoods = 10.0f,
                uncollectedServices = 5.0f,
                uncollectedInvoices = 0.0f,
                uncollectedDCRaSSN = 0.0f,
                unclaimedGift = 0.0f,
                activityCode = 123
            ),
            SummaryXML(
                nature = "N1",
                totalAmount = 200.0f,
                taxableAmount = 150.0f,
                uncollectedAssignmentsGoods = 10.0f,
                uncollectedServices = 5.0f,
                uncollectedInvoices = 0.0f,
                uncollectedDCRaSSN = 0.0f,
                unclaimedGift = 0.0f,
                activityCode = 456
            )
        ),
        sale = SaleXML(
            totalPaymentAmount = 500.0f,
            payDiscount = 50.0f,
            uncollectedInvoice = "SI",
            uncollectedDCRaSSN = "NO",
            uncollectedServices = 20.0f,
            partialAmountsNumber = 2,
            paymentDetails = listOf(
                PaymentDetailsXML(
                    paymentMethod = "Card",
                    partialAmount = 250.0f,
                    paymentAttributes = PaymentAttributesXML(
                        name = "CardType",
                        value = "Credit"
                    )
                )
            )
        ),
        taxableElements = listOf(
            TaxableElementsXML(
                description = "Product Example",
                goodsOrService = "Goods",
                quantity = 2.0f,
                unitPrice = 100.0f,
                totalAmount = 200.0f,
                discount = 10.0f,
                depositPaidGoodsNotDelivered = "DepositExample",
                gift = "GiftExample",
                totalAmountAfterDiscount = "190.0",
                vatVentilation = "SI",
                taxable = 180.0f
            )
        )
    )

    val xml = "<CommercialDocument>" +
            "<DatiConfigurazioneDC>" +
            "<Formato>DSW10</Formato>" +
            "<HashJournalPrec>hashJournalPrec</HashJournalPrec>" +
            "<HashElemJournalPrec>hashElemJournalPrec</HashElemJournalPrec>" +
            "<MatricolaPEM>MatricolaPEM</MatricolaPEM>" +
            "</DatiConfigurazioneDC>" +
            "<CedentePrestatore>" +
            "<PartitaIva>11359591002</PartitaIva>" +
            "<CodiceFiscale>DNTCRL65S67M126L</CodiceFiscale>" +
            "<Anagrafica>" +
            "<Nome>Mario</Nome>" +
            "<Cognome>Rossi</Cognome>" +
            "<Sede>" +
            "<Indirizzo>Via Roma</Indirizzo>" +
            "<Civico>123</Civico>" +
            "<CAP>00100</CAP>" +
            "<Comune>Roma</Comune>" +
            "<Provincia>RM</Provincia>" +
            "<Nazione>Italia</Nazione>" +
            "<TipoSede>0</TipoSede>" +
            "</Sede>" +
            "</Anagrafica>" +
            "</CedentePrestatore>" +
            "<DocumentoCommerciale>" +
            "<CessionarioCommittente>IT12345678901</CessionarioCommittente>" +
            "<DataOra>2024-09-16T15:30:10</DataOra>" +
            "<NumeroProgressivo>000123</NumeroProgressivo>" +
            "<Riepilogo>" +
            "<IVA>" +
            "<AliquotaIVA>22.0</AliquotaIVA>" +
            "<ImpostaTotaleIVA>100.0</ImpostaTotaleIVA>" +
            "</IVA>" +
            "<AmmontareComplessivo>200.0</AmmontareComplessivo>" +
            "<TotaleImponibile>150.0</TotaleImponibile>" +
            "<NonRiscossoCessioneBeni>10.0</NonRiscossoCessioneBeni>" +
            "<NonRiscossoServizi>5.0</NonRiscossoServizi>" +
            "<NonRiscossoFatture>0.0</NonRiscossoFatture>" +
            "<NonRiscossoDCRaSSN>0.0</NonRiscossoDCRaSSN>" +
            "<NonRiscossoOmaggio>0.0</NonRiscossoOmaggio>" +
            "<CodiceAttivita>123</CodiceAttivita>" +
            "</Riepilogo>" +
            "<Riepilogo>" +
            "<Natura>N1</Natura>" +
            "<AmmontareComplessivo>200.0</AmmontareComplessivo>" +
            "<TotaleImponibile>150.0</TotaleImponibile>" +
            "<NonRiscossoCessioneBeni>10.0</NonRiscossoCessioneBeni>" +
            "<NonRiscossoServizi>5.0</NonRiscossoServizi>" +
            "<NonRiscossoFatture>0.0</NonRiscossoFatture>" +
            "<NonRiscossoDCRaSSN>0.0</NonRiscossoDCRaSSN>" +
            "<NonRiscossoOmaggio>0.0</NonRiscossoOmaggio>" +
            "<CodiceAttivita>456</CodiceAttivita>" +
            "</Riepilogo>" +
            "<Vendita>" +
            "<ImportoTotalePagamento>500.0</ImportoTotalePagamento>" +
            "<ScontoApagare>50.0</ScontoApagare>" +
            "<NonRiscossoFattura>SI</NonRiscossoFattura>" +
            "<NonRiscossoDCRaSSN>NO</NonRiscossoDCRaSSN>" +
            "<NonRiscossoPrestazioniServizi>20.0</NonRiscossoPrestazioniServizi>" +
            "<NumeroImportiParziali>2</NumeroImportiParziali>" +
            "<DettagliPagamento>" +
            "<MetodoPagamento>Card</MetodoPagamento>" +
            "<ImportoParziale>250.0</ImportoParziale>" +
            "<AttributiPagamento>" +
            "<Nome>CardType</Nome>" +
            "<Valore>Credit</Valore>" +
            "</AttributiPagamento>" +
            "</DettagliPagamento>" +
            "</Vendita>" +
            "<ElementiContabili>" +
            "<DescrizioneProdotto>Product Example</DescrizioneProdotto>" +
            "<BeneServizio>Goods</BeneServizio>" +
            "<Quantita>2.0</Quantita>" +
            "<PrezzoUnitario>100.0</PrezzoUnitario>" +
            "<ImportoTotale>200.0</ImportoTotale>" +
            "<Sconto>10.0</Sconto>" +
            "<AccontoVersatoBeniNonConsegnati>DepositExample</AccontoVersatoBeniNonConsegnati>" +
            "<Omaggio>GiftExample</Omaggio>" +
            "<ImportoTotaleScontato>190.0</ImportoTotaleScontato>" +
            "<VentilazioneIVA>SI</VentilazioneIVA>" +
            "<Imponibile>180.0</Imponibile>" +
            "</ElementiContabili>" +
            "</DocumentoCommerciale>" +
            "</CommercialDocument>"

    val document = CommercialDocumentXML(dataConfig, merchant, cd)

    @Test
    fun testCommercialDocumentDataXMLDeserialization() {

        val decoded : CommercialDocumentXML= XML.decodeFromString(CommercialDocumentXML.serializer(), xml)
        assertEquals(decoded, document)
    }

    @Test
    fun testCommercialDocumentDataXMLSerializationAndDeserialization() {
        val encoded = XML.encodeToString(CommercialDocumentXML.serializer(), document)
        val decoded = XML.decodeFromString(CommercialDocumentXML.serializer(), encoded)
        assertEquals(document, decoded)
    }

    @Test
    fun testCommercialDocumentDataXMLSerialization() {
        //cambia l'ordine di alcune righe
        val encoded = XML.encodeToString(CommercialDocumentXML.serializer(), document)
        assertEquals(xml, encoded)
    }



    @Test
    fun testLista(){
        val prova = ProvaLista(
            id = "1",
            persona = listOf(
                PersonXML(
                    cf = "DNTCRL65S67M126L",
                    name = "Mario",
                    surname = "Rossi",
                    birthday = "1990-01-01",
                    city = "Rome"
                ),
                PersonXML(
                    cf = "ALVOEWLCL",
                    name = "secondaPersona",
                    surname = "Rossi",
                    birthday = "1990-01-01",
                    city = "Rome"
                )
            )
        )

        println(prova)
        val expected = "<prova>" +
                "<id>1</id>" +
                "<persone>" +
                "<cf>DNTCRL65S67M126L</cf>" +
                "<name>Mario</name>" +
                "<surname>Rossi</surname>" +
                "<birthday>1990-01-01</birthday>" +
                "<city>Rome</city></persone><persone>" +
                "<cf>ALVOEWLCL</cf>" +
                "<name>secondaPersona</name>" +
                "<surname>Rossi</surname>" +
                "<birthday>1990-01-01</birthday>" +
                "<city>Rome</city></persone>" +
                "</prova>"
        assertEquals(expected, XML.encodeToString(prova))
    }
}