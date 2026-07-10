package com.devgate.audit.dto

import com.devgate.audit.models.Action
import com.devgate.audit.models.Target
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class LogMessagePayloadTest {

	@Test
	fun `toAuditLog maps every field`() {
		val actorId = UUID.randomUUID()
		val target = Target().apply {
			type = "service"
			id = "svc-1"
			label = "Payments"
		}
		val createdAt = LocalDateTime.of(2026, 5, 5, 12, 0)
		val payload = LogMessagePayload(
			action = Action.SERVICE_CREATED,
			actorId = actorId,
			target = target,
			payload = """{"k":"v"}""",
			createdAt = createdAt,
		)

		val log = payload.toAuditLog()

		assertThat(log.id).isNull()
		assertThat(log.action).isEqualTo(Action.SERVICE_CREATED)
		assertThat(log.actorId).isEqualTo(actorId)
		assertThat(log.target).isSameAs(target)
		assertThat(log.payload).isEqualTo("""{"k":"v"}""")
		assertThat(log.createdAt).isEqualTo(createdAt)
	}

	@Test
	fun `toAuditLog defaults createdAt to now when it is null`() {
		val before = LocalDateTime.now()

		val log = LogMessagePayload(action = Action.USER_CREATED).toAuditLog()

		val after = LocalDateTime.now()
		assertThat(log.createdAt).isBetween(before, after)
		assertThat(log.action).isEqualTo(Action.USER_CREATED)
		assertThat(log.actorId).isNull()
		assertThat(log.target).isNull()
		assertThat(log.payload).isNull()
	}

	@Test
	fun `toAuditLog handles a fully empty payload`() {
		val before = LocalDateTime.now()

		val log = LogMessagePayload().toAuditLog()

		assertThat(log.action).isNull()
		assertThat(log.actorId).isNull()
		assertThat(log.target).isNull()
		assertThat(log.payload).isNull()
		assertThat(log.createdAt).isAfterOrEqualTo(before)
	}
}
