package com.hmcompany.barber_api.data.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "services")
open class Service(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long,

    @Column(nullable = false, unique = true)
    open val name: String,

    @Column
    open var description: String? = null,

    @Column(nullable = false)
    open var price: Long,

    @Column(nullable = false)
    open var durationMinutes: Int,
)
