package com.hmcompany.barber_api.data.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "ratings_barber")
open class RatingBarber(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    @Column(nullable = false)
    open var rate: Int,

    @ManyToOne
    @JoinColumn(name = "client_id")
    open var client: Client,

    @ManyToOne
    @JoinColumn(name = "barber_id")
    open var barber: Barber
)

/*
Avaliação do cliente do barbeiro
 */