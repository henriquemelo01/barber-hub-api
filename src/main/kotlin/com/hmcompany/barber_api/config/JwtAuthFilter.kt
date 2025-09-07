package com.hmcompany.barber_api.config

import com.hmcompany.barber_api.services.authentication.JwtService
import com.hmcompany.barber_api.services.authentication.UserAccountService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userAccountService: UserAccountService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(AUTHORIZATION_HEADER)

        if (header == null || !header.startsWith(BEARER_TOKEN_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = header.substring(7)
        val userName = jwtService.extractUsernameFromJwtToken(jwtToken)

        userName?.takeIf { SecurityContextHolder.getContext().authentication == null }?.let {
            val user = userAccountService.loadUserByUsername(it)
            if (jwtService.isTokenValid(jwtToken, user)) {
                val authenticatedUser = UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.authorities
                ).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(request)
                }
                SecurityContextHolder.getContext().authentication = authenticatedUser
            }
        }

        filterChain.doFilter(request, response)
    }

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_TOKEN_PREFIX = "Bearer"
    }
}