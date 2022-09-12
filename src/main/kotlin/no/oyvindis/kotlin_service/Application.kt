package no.oyvindis.kotlin_service

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@ConfigurationPropertiesScan
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
