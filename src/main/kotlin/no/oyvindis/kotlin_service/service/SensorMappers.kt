package no.oyvindis.kotlin_service.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.oyvindis.kotlin_service.model.Reading
import no.oyvindis.kotlin_service.model.Sensor

@JsonIgnoreProperties(ignoreUnknown = true)
fun Sensor.mapForCreation(): Reading {
    return Reading(
        battery = battery,
        co2 = co2,
        humidity = humidity,
        pm1 = pm1,
        pm25 = pm25,
        pressure = pressure,
        temp = temp,
        voc = voc
    )
}