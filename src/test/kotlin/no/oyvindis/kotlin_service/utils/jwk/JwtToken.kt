package no.oyvindis.kotlin_service.utils.jwk

import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.time.Instant
import java.util.*


data class JwtToken(
    val iss: String = "http://localhost:6970/adfs",
    val aud: List<String> = listOf("api://default"),
    val exp: Instant = Instant.now().plusSeconds(3600)
) {

    val encoded: String

    init {
        val claimset = JWTClaimsSet.Builder()
            .issuer(iss)
            .audience(aud)
            .expirationTime(Date(exp.toEpochMilli()))
            // .claim("iss", "https://dev-22151406.okta.com/oauth2/default")
            .claim("user_name","test.user@test.no")
            .claim("name", "TEST USER")
            .claim("given_name", "TEST")
            .claim("family_name", "USER")
            // .claim("authorities", access.authorities)
            .build()

        val signed = SignedJWT(JwkStore.header, claimset)
        signed.sign(JwkStore.signer)
        encoded = signed.serialize()
    }
}

//enum class Access(val authorities: String) {
//    ORG_READ("organization:123456789:read,organization:246813579:read,organization:111111111:read,organization:111222333:read"),
//    ORG_WRITE("organization:123456789:admin,organization:246813579:write,organization:111111111:write,organization:111222333:admin"),
//    ROOT("system:root:admin")
//}
