package no.oyvindis.kotlin_service.utils

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import no.oyvindis.kotlin_service.utils.jwk.JwkStore

const val LOCAL_SERVER_PORT = 5000

private val mockserver = WireMockServer(LOCAL_SERVER_PORT)

fun startMockServer() {
    if(!mockserver.isRunning) {
        mockserver.stubFor(get(urlEqualTo("/ping"))
            .willReturn(aResponse().withStatus(200)))
        mockserver.stubFor(get(urlEqualTo("/oauth2/default"))
            .willReturn(okJson(JwkStore.get())))
        mockserver.start()
    }
}

fun stopMockServer() {

    if (mockserver.isRunning) mockserver.stop()

}
