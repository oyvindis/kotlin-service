package no.oyvindis.kotlin_service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("slack.security")
data class SlackSecurity(
    val token: String
)