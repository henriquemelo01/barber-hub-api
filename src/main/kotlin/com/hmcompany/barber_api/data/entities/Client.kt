package com.hmcompany.barber_api.data.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "clients")
open class Client(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    @Column(nullable = false)
    open val firstName: String,

    @Column(nullable = false)
    open val lastName: String,

    @Column(nullable = false, unique = true)
    open var email: String,

    @Column
    open var phone: String? = null,

    @OneToMany(mappedBy = "client")
    open val schedules: MutableList<Scheduling> = mutableListOf(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    open val account: UserAccount,
)
