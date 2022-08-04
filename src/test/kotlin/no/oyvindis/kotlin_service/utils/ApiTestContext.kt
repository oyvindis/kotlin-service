package no.oyvindis.kotlin_service.utils

import org.slf4j.LoggerFactory
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

abstract class ApiTestContext {

    @LocalServerPort
    var port: Int = 0

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.data.mongodb.uri=mongodb://$MONGO_USER:$MONGO_PASSWORD@localhost:${mongoContainer.getMappedPort(MONGO_PORT)}/$MONGO_DB_NAME?authSource=admin&authMechanism=SCRAM-SHA-1"
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    companion object {

        private val logger = LoggerFactory.getLogger(ApiTestContext::class.java)
        var mongoContainer: KGenericContainer

        init {

            startMockServer()

            mongoContainer = KGenericContainer("mongo:latest")
                .withEnv(MONGO_ENV_VALUES)
                .withExposedPorts(MONGO_PORT)
                .waitingFor(Wait.forListeningPort())

            mongoContainer.start()

            populate()

            try {
                val con = URL("http://localhost:5000/ping").openConnection() as HttpURLConnection
                con.connect()
                if (con.responseCode != 200) {
                    logger.debug("Ping to mock server failed")
                    stopMockServer()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }

}

// Hack needed because testcontainers use of generics confuses Kotlin
class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
