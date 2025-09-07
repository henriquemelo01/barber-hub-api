package com.hmcompany.barber_api.data.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "schedules")
open class Scheduling(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long,

    @Column(nullable = false)
    open var dateHour: OffsetDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var status: SchedulingStatus = SchedulingStatus.SCHEDULED,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    open var client: Client,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id")
    open var barber: Barber?,

    @ManyToMany
    @JoinTable(
        name = "scheduling_service",
        joinColumns = [JoinColumn(name = "agendamento_id")],
        inverseJoinColumns = [JoinColumn(name = "servico_id")]
    )
    open val services: MutableList<Service> = mutableListOf(),
)

enum class SchedulingStatus {
    SCHEDULED,
    CONFIRMED,
    DONE,
    CANCELED,;
}
