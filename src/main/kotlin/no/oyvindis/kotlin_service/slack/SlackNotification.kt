package no.oyvindis.kotlin_service.slack

import com.slack.api.methods.MethodsClient
import com.slack.api.methods.response.chat.ChatPostMessageResponse
import no.oyvindis.kotlin_service.config.SlackChannel
import org.springframework.stereotype.Component

@Component
class SlackNotification (
    private val slackMethodsClient: MethodsClient,
    private val slackChannel: SlackChannel
        ) {

    fun notify(message: String): ChatPostMessageResponse = slackMethodsClient.chatPostMessage { it
        .channel(slackChannel.id)
        .text(message)
    }
}