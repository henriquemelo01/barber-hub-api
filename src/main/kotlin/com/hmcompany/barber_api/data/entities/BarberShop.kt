package com.hmcompany.barber_api.data.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint


@Entity
@Table(
    name = "barber_shops",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["zipCode", "complement"])
    ]
)
open class BarberShop(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    @Column(nullable = false)
    open var name: String,

    @Column
    open val cnpj: String? = null,

    @Column(nullable = false)
    open var zipCode: String,

    @Column
    open var complement: String? = null,

    @Column(unique = true)
    open var email: String? = null,

    @Column
    open var website: String? = null,

    @Column(nullable = false, unique = true)
    open var phoneNumber: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "administrator_id", nullable = false)
    open val administrator: Administrator,
)