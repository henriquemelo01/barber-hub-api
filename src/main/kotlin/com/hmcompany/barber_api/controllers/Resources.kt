package com.hmcompany.barber_api.controllers

object Resources {
    private const val BASE_V1 = "/api/v1"

    const val ADMINS= "$BASE_V1/admins"
    const val PING = "$BASE_V1/ping"
    const val BARBER_SHOPS = "$BASE_V1/barber-shops"
}