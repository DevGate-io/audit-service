package com.devgate.audit.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SecurityConfig {

	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		http.cors { it.configurationSource { corsConfiguration() } }
			.csrf { it.disable() }
			.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
			.authorizeHttpRequests {
				it.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
				it.anyRequest().permitAll()
			}

		return http.build()
	}

	fun corsConfiguration(): CorsConfiguration {
		return CorsConfiguration().apply {
			allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			allowedHeaders = listOf("*")
			exposedHeaders = listOf("Set-Cookie")
			allowCredentials = true
			maxAge = 3600L
		}
	}
}