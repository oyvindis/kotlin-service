package no.oyvindis.kotlin_service.utils

import no.oyvindis.kotlin_service.model.LocationDBO
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