package no.oyvindis.kotlin_service.consumer

import no.oyvindis.kotlin_service.service.ReadingService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import no.oyvindis.kotlin_service.model.Sensor
import no.oyvindis.kotlin_service.service.mapForCreation

@Component
class Consumer (
    private val readingService: ReadingService
    ) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.sensor}"], groupId = "ppr")
    fun listenGroupFoo(consumerRecord: ConsumerRecord<String, Sensor>, ack: Acknowledgment) {
        consumerRecord.value()?.let {
            readingService.insert(it.mapForCreation(), it.location ?: "0")
        }
        logger.info("Message received {}", consumerRecord)
        System.out.println(consumerRecord.toString())
        ack.acknowledge()
    }
}