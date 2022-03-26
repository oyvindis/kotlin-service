package no.oyvindis.kotlin_service.service

import no.oyvindis.kotlin_service.model.Location
import no.oyvindis.kotlin_service.repository.LocationDAO
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationDAO: LocationDAO
) {
    fun getLocations(): List<Location> = locationDAO.findAll()
        .map { it.toDTO()}
}
