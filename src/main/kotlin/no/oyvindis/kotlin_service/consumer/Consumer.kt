package no.oyvindis.kotlin_service.consumer

import no.oyvindis.kotlin_service.service.ReadingService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import no.oyvindis.kotlin_service.model.Sensor
import no.oyvindis.kotlin_service.service.mapForCreation
import no.oyvindis.kotlin_service.slack.SlackNotification
import java.time.LocalDateTime

@Component
class Consumer (
    private val readingService: ReadingService,
    private val slackNotification: SlackNotification
    ) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.sensor}"], groupId = "ppr")
    fun listenGroupFoo(consumerRecord: ConsumerRecord<String, Sensor>, ack: Acknowledgment) {
        logger.info("Message received {}", consumerRecord)
        consumerRecord.value()?.let {
            if (LocalDateTime.now().hour == 12 && LocalDateTime.now().minute < 16) {
                readingService.insert(it.mapForCreation(), it.location ?: "0")
            }
            if (it.co2?.toDoubleOrNull()!! >= 1000) {
                slackNotification.notify("CO2-nivået er høyt: ${it.co2}")
            }
        }
        ack.acknowledge()
    }
}