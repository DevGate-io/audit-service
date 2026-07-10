package com.devgate.audit.controllers

import com.devgate.audit.config.SecurityConfig
import com.devgate.audit.models.Action
import com.devgate.audit.models.AuditLog
import com.devgate.audit.services.AuditLogService
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.UUID

@WebMvcTest(AuditLogController::class)
@Import(SecurityConfig::class)
class AuditLogControllerTest {

	@org.springframework.beans.factory.annotation.Autowired
	private lateinit var mockMvc: MockMvc

	@MockitoBean
	private lateinit var auditLogService: AuditLogService

	@Test
	fun `GET audit returns 200 with the serialized logs`() {
		val log = AuditLog().apply {
			id = 1L
			action = Action.SERVICE_CREATED
			actorId = UUID.fromString("00000000-0000-0000-0000-000000000001")
			payload = "p"
			createdAt = LocalDateTime.of(2026, 4, 1, 9, 0)
		}
		given(auditLogService.getLogs()).willReturn(listOf(log))

		mockMvc.perform(get("/audit"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$", hasSize<Any>(1)))
			.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].action").value("service.created"))
			.andExpect(jsonPath("$[0].actorId").value("00000000-0000-0000-0000-000000000001"))
			.andExpect(jsonPath("$[0].payload").value("p"))
	}

	@Test
	fun `GET audit returns 200 with an empty array when there are no logs`() {
		given(auditLogService.getLogs()).willReturn(emptyList())

		mockMvc.perform(get("/audit"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$", hasSize<Any>(0)))
	}
}
