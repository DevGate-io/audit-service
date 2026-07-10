package com.devgate.audit.services.impl

import com.devgate.audit.dto.LogMessagePayload
import com.devgate.audit.models.Action
import com.devgate.audit.models.AuditLog
import com.devgate.audit.models.Target
import com.devgate.audit.services.AuditLogService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.willThrow
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import java.util.UUID

class MessageServiceImplTest {

	private lateinit var auditLogService: AuditLogService
	private lateinit var messageService: MessageServiceImpl

	@BeforeEach
	fun setUp() {
		auditLogService = mock(AuditLogService::class.java)
		messageService = MessageServiceImpl(auditLogService)
	}

	/**
	 * Registers an `any()` matcher while returning a non-null placeholder, so Kotlin's
	 * non-null parameter check does not trip over the `null` that Mockito matchers return.
	 */
	private fun anyAuditLog(): AuditLog {
		ArgumentMatchers.any(AuditLog::class.java)
		return AuditLog()
	}

	@Test
	fun `listener converts the payload and saves it`() {
		val actorId = UUID.randomUUID()
		val createdAt = LocalDateTime.of(2026, 2, 2, 8, 0)
		val target = Target().apply { type = "team"; id = "t-1"; label = "Core" }
		val payload = LogMessagePayload(
			action = Action.TEAM_CREATED,
			actorId = actorId,
			target = target,
			payload = "data",
			createdAt = createdAt,
		)

		messageService.listener(payload)

		val captor = ArgumentCaptor.forClass(AuditLog::class.java)
		then(auditLogService).should().saveLog(captor.capture() ?: AuditLog())
		val saved = captor.value
		assertThat(saved.action).isEqualTo(Action.TEAM_CREATED)
		assertThat(saved.actorId).isEqualTo(actorId)
		assertThat(saved.target).isSameAs(target)
		assertThat(saved.payload).isEqualTo("data")
		assertThat(saved.createdAt).isEqualTo(createdAt)
	}

	@Test
	fun `listener propagates exceptions from the service`() {
		willThrow(RuntimeException("db is down"))
			.given(auditLogService).saveLog(anyAuditLog())

		assertThrows(RuntimeException::class.java) {
			messageService.listener(LogMessagePayload(action = Action.USER_DELETED))
		}
	}
}
