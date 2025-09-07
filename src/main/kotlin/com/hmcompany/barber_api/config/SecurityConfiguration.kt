package com.hmcompany.barber_api.config

import com.hmcompany.barber_api.controllers.Resources
import com.hmcompany.barber_api.services.authentication.JwtService
import com.hmcompany.barber_api.services.authentication.UserAccountService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    private val jwtService: JwtService,
    private val userAccountService: UserAccountService,
) {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers(Resources.ADMINS + "/login").permitAll()
            it.requestMatchers(Resources.ADMINS + "/register").permitAll()
            it.anyRequest().authenticated()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(
            JwtAuthFilter(jwtService = jwtService, userAccountService = userAccountService),
            UsernamePasswordAuthenticationFilter::class.java
        )
        .build()
}