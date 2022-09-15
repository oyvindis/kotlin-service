package no.oyvindis.kotlin_service.security

import com.okta.jwt.AccessTokenVerifier
import com.okta.jwt.JwtVerifiers
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
open class SecurityConfig {
    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .cors().and()
            .authorizeRequests { authorize ->
                authorize.antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers(HttpMethod.GET,"/climate-api/reading/ping").permitAll()
                    .antMatchers(HttpMethod.GET, "/climate-api/reading/ready").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { resourceServer -> resourceServer.jwt() }
        return http.build()
    }

    @Bean
    open fun jwtDecoder(properties: OAuth2ResourceServerProperties): AccessTokenVerifier? {
        val jwtDecoder: AccessTokenVerifier = JwtVerifiers.accessTokenVerifierBuilder()
            .setIssuer("https://dev-22151406.okta.com/oauth2/default")
            .setAudience("api://default")
            .build()
        return jwtDecoder
    }

//    @Bean
//    open fun jwtDecoder(properties: OAuth2ResourceServerProperties): JwtDecoder {
//        val jwtDecoder = NimbusJwtDecoder.withJwkSetUri(properties.jwt.jwkSetUri).build()
//        jwtDecoder.setJwtValidator(
//            DelegatingOAuth2TokenValidator(
//                listOf(
//                    JwtTimestampValidator(),
//                    JwtIssuerValidator(properties.jwt.issuerUri)
//                )
//            ))
//        return jwtDecoder
//    }
}