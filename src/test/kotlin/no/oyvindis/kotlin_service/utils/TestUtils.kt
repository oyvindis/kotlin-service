package no.oyvindis.kotlin_service.utils

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import no.oyvindis.kotlin_service.model.LocationDBO
import no.oyvindis.kotlin_service.utils.ApiTestContext.Companion.mongoContainer
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.springframework.http.*
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL


fun apiGet(port: Int, endpoint: String, acceptHeader: String?): Map<String,Any> {

    return try {
        val connection = URL("http://localhost:$port$endpoint").openConnection() as HttpURLConnection
        if(acceptHeader != null) connection.setRequestProperty("Accept", acceptHeader)
        connection.connect()

        if(isOK(connection.responseCode)) {
            val responseBody = connection.inputStream.bufferedReader().use(BufferedReader::readText)
            mapOf(
                "body"   to responseBody,
                "header" to connection.headerFields,
                "status" to connection.responseCode)
        } else {
            mapOf(
                "status" to connection.responseCode,
                "header" to " ",
                "body"   to " "
            )
        }
    } catch (e: Exception) {
        mapOf(
            "status" to e.toString(),
            "header" to " ",
            "body"   to " "
        )
    }
}

private fun isOK(response: Int?): Boolean = HttpStatus.resolve(response ?: 0)?.is2xxSuccessful ?: false

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

}

private fun LocationDBO.mapBDO(): org.bson.Document =
    org.bson.Document()
        .append("_id", id)
        .append("name", name)

fun locationDbPopulation() = listOf(LOCATION_1000)
    .map { it.mapBDO() }

