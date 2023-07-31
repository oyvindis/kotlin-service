package no.oyvindis.kotlin_service.utils

import no.oyvindis.kotlin_service.model.LocationDBO
import no.oyvindis.kotlin_service.model.ReadingDBO
import java.time.LocalDateTime


private fun LocationDBO.mapBDO(): org.bson.Document =
    org.bson.Document()
        .append("_id", id)
        .append("name", name)

fun locationDbPopulation() = listOf(LOCATION_1000)
    .map { it.mapBDO() }

private fun ReadingDBO.mapBDO(): org.bson.Document =
    org.bson.Document()
        .append("_id", id)
        .append("location", location)
        .append("localDateTime", LocalDateTime.now())
        .append("battery", battery)
        .append("co2", co2)
        .append("humidity", humidity)
        .append("pm1", pm1)
        .append("pm25", pm25)
        .append("pressure", pressure)
        .append("temp", temp)
        .append("time", time)
        .append("voc", voc)

fun readingDbPopulation() = listOf(READING_1000)
    .map { it.mapBDO() }

