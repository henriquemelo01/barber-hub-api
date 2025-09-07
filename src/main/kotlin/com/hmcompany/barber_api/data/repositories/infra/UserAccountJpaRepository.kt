package com.hmcompany.barber_api.data.repositories.infra

import com.hmcompany.barber_api.data.entities.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountJpaRepository: JpaRepository<UserAccount, Long> {
    fun findByEmail(email: String): UserAccount?
}