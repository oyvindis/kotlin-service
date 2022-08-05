package no.oyvindis.kotlin_service.utils

import no.oyvindis.kotlin_service.model.Location
import no.oyvindis.kotlin_service.model.LocationDBO
import no.oyvindis.kotlin_service.model.ReadingDBO
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap

const val MONGO_USER = "testuser"
const val MONGO_PASSWORD = "testpassword"
const val MONGO_PORT = 27017
const val MONGO_DB_NAME = "climate"

val MONGO_ENV_VALUES: Map<String, String> = ImmutableMap.of(
    "MONGO_INITDB_ROOT_USERNAME", MONGO_USER,
    "MONGO_INITDB_ROOT_PASSWORD", MONGO_PASSWORD
)

val LOCATION_ID_1000 = "1000"

val LOCATION_NAME_1000 = "Location_1000"

val LOCATION_1000 = LocationDBO(
    id = "1000",
    name = "Location_1000"
)

val LOCATION_0 = Location(
    id = "id0",
    name = "location 0"
)

val READING_1000 = ReadingDBO(
    id = "1",
    location = "1000",
    battery = "66",
    co2 = "784.0",
    humidity = "22.0",
    pm1 = "2.0",
    pm25 = "2.0",
    pressure = "999.8",
    temp = "25.0",
    time = 1650660311,
    voc = "694.0"
)
