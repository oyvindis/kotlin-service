package no.oyvindis.kotlin_service.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("slack.security")
data class SlackSecurity(
    val token: String
)