package no.oyvindis.kotlin_service.utils.jwk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*

/*
 * Methods for starting and stopping a WireMockServer that mocks an OAuth2 server.
 */

private const val PORT = 6970

private val mockServer = WireMockServer(PORT)

fun startMockOAuthServer() {
    if (!mockServer.isRunning) {

        val openIdConfig = """
        {
           "issuer": "http://localhost:$PORT/adfs",
           "jwks_uri": "http://localhost:$PORT/adfs/discovery/keys"
        }""".trimIndent()

        val publicKeys = """
        {
           "keys": [
                ${JwkStore.pulicKey.toJSONString()}
           ]
        }""".trimIndent()

        mockServer.stubFor(
            get(urlEqualTo("/adfs/.well-known/openid-configuration"))
                .willReturn(okJson(openIdConfig))
        )

        mockServer.stubFor(
            get(urlEqualTo("/adfs/discovery/keys"))
                .willReturn(
                    okJson(publicKeys)
                )
        )

        mockServer.start()
    }
}

fun stopMockOAuthServer() {
    if (mockServer.isRunning) {
        mockServer.stop()
    }
}