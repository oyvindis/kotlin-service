package no.oyvindis.kotlin_service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("slack.channel")
data class SlackChannel(
    val id: String
)