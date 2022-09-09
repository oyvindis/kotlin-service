package no.oyvindis.kotlin_service.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import no.oyvindis.kotlin_service.model.Sensor

import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory
import kotlin.text.Charsets.UTF_8


class SensorDeserializer : Deserializer<Sensor> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun deserialize(topic: String?, data: ByteArray?): Sensor? {
        log.info("Deserializing...")
        return objectMapper.readValue(
            String(
                data ?: throw SerializationException("Error when deserializing byte[] to Sensor"), UTF_8
            ), Sensor::class.java
        )
    }

    override fun close() {}

}
