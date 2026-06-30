package com.devgate.audit.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

	@Bean
	fun openApi(): OpenAPI {
		return OpenAPI()
			.info(
				Info()
					.title("Audit Service API")
					.description("API для аудита событий")
					.version("1.0.0")
			)
	}
}
