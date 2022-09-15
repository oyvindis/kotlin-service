package no.oyvindis.kotlin_service.utils.jwk

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import java.util.*


object JwkStore{
    private val jwk = createJwk()

    private fun createJwk(): RSAKey =
        RSAKeyGenerator(2048)
            .algorithm(JWSAlgorithm.RS256)
            .keyUse(KeyUse.SIGNATURE)
            .keyID(UUID.randomUUID().toString())
            .generate()

    fun get(): String {
        val token : JwkToken = jacksonObjectMapper()
            .readValue(jwk.toJSONString())
        return token.toString()
    }

    fun jwtHeader() =
        JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(jwk.keyID)
            .build()

    fun signer() =
        RSASSASigner(jwk)

}

@JsonIgnoreProperties(ignoreUnknown = true)
class JwkToken(
    private val kid : String,
    private val kty :String,
    private val use : String,
    private val n : String,
    private val e : String
){

    override fun toString(): String =
        """{
            "keys": [
                {
                    "kid": "$kid",
                    "kty": "$kty",
                    "alg": "RS256",
                    "use": "$use",
                    "n": "$n",
                    "e": "$e"
                }
            ]
        }""".trimIndent()

}
