package com.hmcompany.barber_api.services.authentication

import com.hmcompany.barber_api.data.repositories.infra.UserAccountJpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserAccountService(
    private val userAccountJpaRepository: UserAccountJpaRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userAccountJpaRepository.findByEmail(username) ?: throw UsernameNotFoundException(
            USER_NOT_FOUND_MESSAGE
        )

    private companion object {
        const val USER_NOT_FOUND_MESSAGE = "Usuário inválido"
    }
}