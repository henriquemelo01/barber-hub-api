package com.hmcompany.barber_api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String
    var expiration: Long = 0
    lateinit var issuer: String
}