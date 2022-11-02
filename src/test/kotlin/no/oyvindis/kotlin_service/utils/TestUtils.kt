package no.oyvindis.kotlin_service.utils

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import no.oyvindis.kotlin_service.model.LocationDBO
import no.oyvindis.kotlin_service.model.ReadingDBO
import no.oyvindis.kotlin_service.utils.ApiTestContext.Companion.mongoContainer
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import java.time.LocalDateTime


fun populate() {
    val connectionString = ConnectionString("mongodb://${MONGO_USER}:${MONGO_PASSWORD}@localhost:${mongoContainer.getMappedPort(MONGO_PORT)}/$MONGO_DB_NAME?authSource=admin&authMechanism=SCRAM-SHA-1")
    val pojoCodecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true).build()))

    val client: MongoClient = MongoClients.create(connectionString)
    val mongoDatabase = client.getDatabase(MONGO_DB_NAME).withCodecRegistry(pojoCodecRegistry)

    val locationCollection = mongoDatabase.getCollection("location")
    locationCollection.deleteMany(org.bson.Document())
    locationCollection.insertMany(locationDbPopulation())

    val readingCollection = mongoDatabase.getCollection("reading")
    readingCollection.deleteMany(org.bson.Document())
    readingCollection.insertMany(readingDbPopulation())
}

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

