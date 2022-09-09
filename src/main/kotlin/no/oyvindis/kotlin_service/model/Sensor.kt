package no.oyvindis.kotlin_service.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

data class Sensor(
    @JsonProperty("location")
    val location: String?,
    @JsonProperty("battery")
    val battery: String? = null,
    @JsonProperty("co2")
    val co2: String? = null,
    @JsonProperty("humidity")
    val humidity: String? = null,
    @JsonProperty("pm1")
    val pm1: String? = null,
    @JsonProperty("pm25")
    val pm25: String? = null,
    @JsonProperty("pressure")
    val pressure: String? = null,
    @JsonProperty("temp")
    val temp: String? = null,
    @JsonProperty("voc")
    val voc: String? = null
)
