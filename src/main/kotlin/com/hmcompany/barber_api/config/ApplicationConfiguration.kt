package com.hmcompany.barber_api.config

import com.hmcompany.barber_api.data.repositories.infra.UserAccountJpaRepository
import com.hmcompany.barber_api.services.authentication.UserAccountService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationConfiguration(
    private val userAccountJpaRepository: UserAccountJpaRepository,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService = UserAccountService(userAccountJpaRepository)

    @Bean
    fun authenticationProvider(): AuthenticationProvider =
        DaoAuthenticationProvider(userDetailsService())
            .apply { setPasswordEncoder(passwordEncoder()) }

    @Bean
    fun authenticationManager(
        config: AuthenticationConfiguration
    ): AuthenticationManager = config.authenticationManager

}