package no.oyvindis.kotlin_service.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "reading")
data class ReadingDBO(
    @Id
    val id: String,
    val location: String,
    val battery: String?,
    val co2: String?,
    val humidity: String?,
    val pm1: String?,
    val pm25: String?,
    val pressure: String?,
    val temp: String?,
    val time: Long,
    val voc: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Reading(
    val id: String? = null,
    val location: Optional<LocationDBO>? = null,
    val localDateTime: LocalDateTime? = null,
    val battery: String? = null,
    val co2: String? = null,
    val humidity: String? = null,
    val pm1: String? = null,
    val pm25: String? = null,
    val pressure: String? = null,
    val temp: String? = null,
    val time: Long? = null,
    val voc: String? = null
)
