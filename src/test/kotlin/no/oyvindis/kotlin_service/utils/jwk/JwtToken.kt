package no.oyvindis.kotlin_service.utils.jwk

import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.util.*


// class JwtToken (private val access: Access) {
class JwtToken () {
    private val exp = Date().time + 120 * 1000
    private val aud = listOf("api://default")

    private fun buildToken() : String{
        val claimset = JWTClaimsSet.Builder()
            .audience(aud)
            .expirationTime(Date(exp))
            .claim("iss", "https://dev-22151406.okta.com/oauth2/default")
            .claim("user_name","test.user@test.no")
            .claim("name", "TEST USER")
            .claim("given_name", "TEST")
            .claim("family_name", "USER")
            // .claim("authorities", access.authorities)
            .build()

        val signed = SignedJWT(JwkStore.jwtHeader(), claimset)
        signed.sign(JwkStore.signer())

        return signed.serialize()
    }

    override fun toString(): String {
        return buildToken()
    }

}

//enum class Access(val authorities: String) {
//    ORG_READ("organization:123456789:read,organization:246813579:read,organization:111111111:read,organization:111222333:read"),
//    ORG_WRITE("organization:123456789:admin,organization:246813579:write,organization:111111111:write,organization:111222333:admin"),
//    ROOT("system:root:admin")
//}
