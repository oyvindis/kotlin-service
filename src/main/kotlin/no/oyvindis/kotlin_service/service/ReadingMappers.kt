package no.oyvindis.kotlin_service.service

import no.oyvindis.kotlin_service.model.LocationDBO
import no.oyvindis.kotlin_service.model.Reading
import no.oyvindis.kotlin_service.model.ReadingDBO
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


fun ReadingDBO.toDTO(locationDBO: Optional<LocationDBO>): Reading =
    Reading(
        id = id,
        location = locationDBO,
        localDateTime = Instant.ofEpochSecond(time)
            .atZone(ZoneId.of("Europe/Oslo"))
            .toLocalDateTime(),
        battery = battery,
        co2 = co2,
        humidity = humidity,
        pm1 = pm1,
        pm25 = pm25,
        pressure = pressure,
        temp = temp,
        time = time,
        voc = voc
    )

fun Reading.mapForCreation(location: String): ReadingDBO {
    val newId = UUID.randomUUID().toString()
    val localDateTime = LocalDateTime.now()
    val zdt = ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Oslo"))

    return ReadingDBO(
        id = newId,
        location = location,
        battery = battery,
        co2 = co2,
        humidity = humidity,
        pm1 = pm1,
        pm25 = pm25,
        pressure = pressure,
        temp = temp,
        time = time ?: zdt.toEpochSecond(),
        voc = voc
    )
}