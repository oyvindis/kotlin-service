package no.oyvindis.kotlin_service.service

import no.oyvindis.kotlin_service.model.Location
import no.oyvindis.kotlin_service.model.LocationDBO

fun LocationDBO.toDTO(): Location =
    Location(
        id = id,
        name = name
    )