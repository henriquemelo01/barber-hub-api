package com.hmcompany.barber_api.services.authentication

import com.hmcompany.barber_api.config.JwtProperties
import com.hmcompany.barber_api.data.entities.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class JwtService(
    private val jwtProperties: JwtProperties,
) {
    private val secretKey by lazy {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
        Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(userDetails: UserDetails, extraClaims: Map<String, Any> = mapOf()) = Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.username)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact()

    fun generateToken(userDetails: UserDetails, role: Role, extraClaims: Map<String, Any> = mapOf()) = generateToken(
        userDetails = userDetails,
        extraClaims = mapOf(ROLE_CLAIM_ID to role.name)
    )

    fun extractUsernameFromJwtToken(token: String): String? = extractClaim(token, Claims::getSubject)

    fun extractRoleFromJwtToken(token: String) = extractClaim(token) {
        it[ROLE_CLAIM_ID] as? String
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = token.extractAllClaims()
        return claimsResolver(claims)
    }

    private fun String.extractAllClaims(): Claims = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(this)
        .body

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsernameFromJwtToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String) =
        extractClaim(token, Claims::getExpiration).before(Date(System.currentTimeMillis()))

    private companion object {
        const val ROLE_CLAIM_ID = "role"
    }
}

