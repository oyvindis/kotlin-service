package no.oyvindis.kotlin_service.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import no.oyvindis.kotlin_service.model.ReadingDBO

@Repository
interface ReadingDAO:MongoRepository<ReadingDBO, String> {
    fun findReadingsByLocation(location: String):List<ReadingDBO>
}