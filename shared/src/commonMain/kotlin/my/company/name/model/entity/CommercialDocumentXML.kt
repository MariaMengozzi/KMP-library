package my.company.name.model.entity

import io.ktor.util.encodeBase64
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlChildrenName
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8

@Serializable
@SerialName("CommercialDocument")
@XmlSerialName("CommercialDocument")
data class CommercialDocumentXML (
    val configurationData : ConfigurationDCDataXML,
    val merchant : MerchantXML,
    val commercialDocumentData: CommercialDocumentDataXML,
){
    fun sha256HashBase64() : String {
        val serializedPerson = XML.encodeToString(CommercialDocumentXML.serializer(), this)

        // Converte la stringa serializzata in una ByteString e calcola l'hash SHA-256
        val hash: ByteString = serializedPerson.encodeUtf8().sha256()

        // Converte l'hash in una stringa esadecimale e la ritorna
        return hash.hex().encodeBase64()
    }
}

// ---- configuration data ----
@Serializable
@SerialName("DatiConfigurazioneDC")
@XmlSerialName("DatiConfigurazioneDC", namespace = "", prefix = "")
data class ConfigurationDCDataXML (
    @XmlChildrenName("Formato")
    @XmlSerialName("Formato")
    val format: String,
    @XmlChildrenName("HashJournalPrec")
    @XmlSerialName("HashJournalPrec")
    val hashJournalPrec : String,
    @XmlChildrenName("HashElemJournalPrec")
    @XmlSerialName("HashElemJournalPrec")
    val hashElemJournalPrec: String,
    @XmlChildrenName("MatricolaPEM")
    @XmlSerialName("MatricolaPEM")
    val pemSerialNumber: String
)
// ---- Merchant ----
@Serializable
@SerialName("CedentePrestatore")
@XmlSerialName("CedentePrestatore", namespace = "", prefix = "")
data class MerchantXML (
    @XmlChildrenName("PartitaIva")
    @XmlSerialName("PartitaIva")
    val vat : String,
    @XmlChildrenName("CodiceFiscale")
    @XmlSerialName("CodiceFiscale")
    val cf: String,
    @XmlChildrenName("Anagrafica")
    @XmlSerialName("Anagrafica")
    val registry : RegistryXML,
)

@Serializable
@SerialName("Anagrafica")
@XmlSerialName("Anagrafica", namespace = "", prefix = "")
data class RegistryXML (
    @XmlChildrenName("Denominazione")
    @XmlSerialName("Denominazione")
    val companyName : String? = null,
    @XmlChildrenName("Nome")
    @XmlSerialName("Nome")
    val name : String? = null,
    @XmlChildrenName("Cognome")
    @XmlSerialName("Cognome")
    val surname : String? = null,
    @XmlChildrenName("Sede")
    @XmlSerialName("Sede")
    val site : SiteXML,
)

@Serializable
@SerialName("Sede")
@XmlSerialName("Sede", namespace = "", prefix = "")
data class SiteXML (
    @XmlChildrenName("Indirizzo")
    @XmlSerialName("Indirizzo")
    val address: String,
    @XmlChildrenName("Civico")
    @XmlSerialName("Civico")
    val houseNumber: String,
    @XmlChildrenName("CAP")
    @XmlSerialName("CAP")
    val zipCode: String,
    @XmlChildrenName("Comune")
    @XmlSerialName("Comune")
    val city: String,
    @XmlChildrenName("Provincia")
    @XmlSerialName("Provincia")
    val province: String,
    @XmlChildrenName("Nazione")
    @XmlSerialName("Nazione")
    val country: String,
    @XmlChildrenName("TipoSede")
    @XmlSerialName("TipoSede")
    val siteType: String, // 0 sede legale / 1 luogo di esercizio
)

// ---- Commercial document ----

@Serializable
@SerialName("DocumentoCommerciale")
@XmlSerialName("DocumentoCommerciale", namespace = "", prefix = "")
data class CommercialDocumentDataXML (
    @XmlChildrenName("CessionarioCommittente")
    @SerialName("CessionarioCommittente")
    val clientCf : String? = null,
    @XmlChildrenName("DatiLotteria")
    @SerialName("DatiLotteria")
    val lotteryData : LotteryDataXML? = null,
    @XmlChildrenName("DataOra")
    @SerialName("DataOra")
    val dateTime : LocalDateTime,
    @XmlChildrenName("NumeroProgressivo")
    @SerialName("NumeroProgressivo")
    val progressiveNumber : String,
    @SerialName("Riepilogo")
    @XmlSerialName("Riepilogo")
    val summary : List<SummaryXML>,
    @XmlChildrenName("ResoAnnullo")
    @SerialName("ResoAnnullo")
    val returnCancellation : ReturnCancellationXML? = null,
    @XmlChildrenName("Vendita")
    @SerialName("Vendita")
    val sale : SaleXML? = null,
    @XmlSerialName("ElementiContabili")
    @SerialName("ElementiContabili")
    val taxableElements : List<TaxableElementsXML>,

)

@Serializable
@SerialName("DatiLotteria")
@XmlSerialName("DatiLotteria", namespace = "", prefix = "")
data class LotteryDataXML (
    @XmlChildrenName("CodiceBidimensionaleLI")
    @SerialName("CessionarioCommittente")
    val bidimensionalCode : String,
    @XmlChildrenName("CodiceLotteria")
    @SerialName("CodiceLotteria")
    val lotteryCode : String
)

@Serializable
@SerialName("Riepilogo")
@XmlSerialName("Riepilogo", namespace = "", prefix = "")
data class SummaryXML (
    @XmlChildrenName("IVA")
    @SerialName("IVA")
    val iva : IvaXML? = null,
    @XmlChildrenName("Natura")
    @SerialName("Natura")
    val nature : String? = null,
    @XmlChildrenName("VentilazioneIVA")
    @SerialName("VentilazioneIVA")
    val vatVentilation : String? = null,
    @XmlChildrenName("AmmontareComplessivo")
    @SerialName("AmmontareComplessivo")
    val totalAmount : Float,
    @XmlChildrenName("TotaleImponibile")
    @SerialName("TotaleImponibile")
    val taxableAmount : Float,
    @XmlChildrenName("NonRiscossoCessioneBeni")
    @SerialName("NonRiscossoCessioneBeni")
    val uncollectedAssignmentsGoods : Float,
    @XmlChildrenName("NonRiscossoServizi")
    @SerialName("NonRiscossoServizi")
    val uncollectedServices : Float,
    @XmlChildrenName("NonRiscossoFatture")
    @SerialName("NonRiscossoFatture")
    val uncollectedInvoices : Float,
    @XmlChildrenName("NonRiscossoDCRaSSN")
    @SerialName("NonRiscossoDCRaSSN")
    val uncollectedDCRaSSN : Float,
    @XmlChildrenName("NonRiscossoOmaggio")
    @SerialName("NonRiscossoOmaggio")
    val unclaimedGift : Float,
    @XmlChildrenName("CodiceAttivita")
    @SerialName("CodiceAttivita")
    val activityCode : Int,
)

@Serializable
@SerialName("IVA")
@XmlSerialName("IVA", namespace = "", prefix = "")
data class IvaXML (
    @XmlChildrenName("AliquotaIVA")
    @SerialName("AliquotaIVA")
    val vatRate : Float,
    @XmlChildrenName("ImpostaTotaleIVA")
    @SerialName("ImpostaTotaleIVA")
    val totalVatTax : Float,
)

@Serializable
@SerialName("ResoAnnullo")
@XmlSerialName("ResoAnnullo", namespace = "", prefix = "")
data class ReturnCancellationXML (
    @XmlChildrenName("Tipologia")
    @SerialName("Tipologia")
    val type : String,
    @XmlChildrenName("MatricolaDispositivoEmittente")
    @SerialName("MatricolaDispositivoEmittente")
    val serialNumberDeviceIssuing : String,
    @XmlChildrenName("DataOra")
    @SerialName("DataOra")
    val dateTime : LocalDateTime,
    @XmlChildrenName("NumeroProgressivo")
    @SerialName("NumeroProgressivo")
    val progressiveNumber: String
)

@Serializable
@SerialName("Vendita")
@XmlSerialName("Vendita", namespace = "", prefix = "")
data class SaleXML (
    @XmlChildrenName("ImportoTotalePagamento")
    @SerialName("ImportoTotalePagamento")
    val totalPaymentAmount : Float,
    @XmlChildrenName("ScontoApagare")
    @SerialName("ScontoApagare")
    val payDiscount : Float,
    @XmlChildrenName("NonRiscossoFattura")
    @SerialName("NonRiscossoFattura")
    val uncollectedInvoice : String,
    @XmlChildrenName("NonRiscossoDCRaSSN")
    @SerialName("NonRiscossoDCRaSSN")
    val uncollectedDCRaSSN : String,
    @XmlChildrenName("NonRiscossoPrestazioniServizi")
    @SerialName("NonRiscossoPrestazioniServizi")
    val uncollectedServices : Float,
    @XmlChildrenName("NumeroImportiParziali")
    @SerialName("NumeroImportiParziali")
    val partialAmountsNumber : Int,
    @XmlSerialName("DettagliPagamento")
    @SerialName("DettagliPagamento")
    val paymentDetails : List<PaymentDetailsXML>,
)

@Serializable
@SerialName("DettagliPagamento")
@XmlSerialName("DettagliPagamento", namespace = "", prefix = "")
data class PaymentDetailsXML (
    @XmlChildrenName("MetodoPagamento")
    @SerialName("MetodoPagamento")
    val paymentMethod : String,
    @XmlChildrenName("ImportoParziale")
    @SerialName("ImportoParziale")
    val partialAmount : Float,
    @XmlChildrenName("AttributiPagamento")
    @SerialName("AttributiPagamento")
    val paymentAttributes : PaymentAttributesXML?,
)

@Serializable
@SerialName("AttributiPagamento")
@XmlSerialName("AttributiPagamento", namespace = "", prefix = "")
data class PaymentAttributesXML (
    @XmlChildrenName("Nome")
    @SerialName("Nome")
    val name : String,
    @XmlChildrenName("Valore")
    @SerialName("Valore")
    val value : String,
)

@Serializable
@SerialName("ElementiContabili")
@XmlSerialName("ElementiContabili", namespace = "", prefix = "")
data class TaxableElementsXML (
    @XmlChildrenName("DescrizioneProdotto")
    @SerialName("DescrizioneProdotto")
    val description : String,
    @XmlChildrenName("BeneServizio")
    @SerialName("BeneServizio")
    val goodsOrService : String,
    @XmlChildrenName("Quantita")
    @SerialName("Quantita")
    val quantity : Float,
    @XmlChildrenName("PrezzoUnitario")
    @SerialName("PrezzoUnitario")
    val unitPrice : Float,
    @XmlChildrenName("ImportoTotale")
    @SerialName("ImportoTotale")
    val totalAmount : Float,
    @XmlChildrenName("Sconto")
    @SerialName("Sconto")
    val discount : Float,
    @XmlChildrenName("AccontoVersatoBeniNonConsegnati")
    @SerialName("AccontoVersatoBeniNonConsegnati")
    val depositPaidGoodsNotDelivered : String,
    @XmlChildrenName("Omaggio")
    @SerialName("Omaggio")
    val gift : String,
    @XmlChildrenName("ImportoTotaleScontato")
    @SerialName("ImportoTotaleScontato")
    val totalAmountAfterDiscount : String,
    @XmlChildrenName("IVA")
    @SerialName("IVA")
    val iva : IvaXML? = null,
    @XmlChildrenName("Natura")
    @SerialName("Natura")
    val nature : String? = null,
    @XmlChildrenName("VentilazioneIVA")
    @SerialName("VentilazioneIVA")
    val vatVentilation : String? = null,
    @XmlChildrenName("Imponibile")
    @SerialName("Imponibile")
    val taxable : Float,
    )