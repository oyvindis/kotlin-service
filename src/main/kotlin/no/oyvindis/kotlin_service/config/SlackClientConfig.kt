package no.oyvindis.kotlin_service.config

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SlackClientConfig(
    private val slackSecurity: SlackSecurity
) {
    @Bean
    open fun slackMethodsClient(): MethodsClient? {
        return Slack.getInstance().methods(slackSecurity.token)
    }
}