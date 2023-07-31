package no.oyvindis.kotlin_service.security

import com.okta.jwt.AccessTokenVerifier
import com.okta.jwt.JwtVerifiers
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
open class SecurityConfig {
    @Bean
    @ConditionalOnProperty(name = ["security.disabled"], havingValue = "false", matchIfMissing = true)
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .cors().and()
            .authorizeRequests {
                it.requestMatchers(HttpMethod.OPTIONS).permitAll()
                it.requestMatchers(HttpMethod.GET,"/climate-api/reading/ping").permitAll()
                it.requestMatchers(HttpMethod.GET, "/climate-api/reading/ready").permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt()
            }
        return http.build()
    }

    @Bean
    @ConditionalOnProperty(name = ["security.disabled"], havingValue = "false", matchIfMissing = true)
    open fun jwtDecoder(properties: OAuth2ResourceServerProperties): AccessTokenVerifier? {
        val jwtDecoder: AccessTokenVerifier = JwtVerifiers.accessTokenVerifierBuilder()
            .setIssuer("https://dev-22151406.okta.com/oauth2/default")
            .setAudience("api://default")
            .build()
        return jwtDecoder
    }

    @Bean
    open fun authorizationServerSettings(): AuthorizationServerSettings? {
        return AuthorizationServerSettings.builder().build()
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