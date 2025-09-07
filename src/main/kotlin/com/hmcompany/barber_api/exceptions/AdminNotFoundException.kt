package com.hmcompany.barber_api.exceptions

data class AdminNotFoundException(override val message: String = "Administrador não foi localizado") : Exception()