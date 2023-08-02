package no.oyvindis.kotlin_service.utils.jwk

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import java.util.UUID

object JwkStore {

    /**
     * Public and private keys as a JSON Web Key (JWK)
     */
    private val keyPair: RSAKey =
        RSAKeyGenerator(2048)
            .algorithm(JWSAlgorithm.RS256)
            .keyUse(KeyUse.SIGNATURE)
            .keyID(UUID.randomUUID().toString())
            .generate()

    /**
     * Public key as a JSON Web Key (JWK)
     */
    val pulicKey: RSAKey =
        keyPair.toPublicJWK()

    /**
     * JSON Web Signature (JWS) header. Used as a header in JWT tokens.
     */
    val header: JWSHeader =
        JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(keyPair.keyID)
            .build()

    /**
     * JSON Web Signature (JWS) signer. Used for signing JWT tokens.
     */
    val signer: JWSSigner =
        RSASSASigner(keyPair)

}