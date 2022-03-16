package no.oyvindis.kotlin_service.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "location")
data class LocationDBO(
    @Id val id:String,
    val name:String? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Location (
    val id: String? = null,
    val name: String? = null,
)
