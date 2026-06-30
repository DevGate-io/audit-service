package com.devgate.audit.dto

import com.devgate.audit.models.Action
import com.devgate.audit.models.AuditLog
import com.devgate.audit.models.Target
import java.time.LocalDateTime
import java.util.UUID

data class LogMessagePayload(
	val action: Action? = null,
	val actorId: UUID? = null,
	val target: Target? = null,
	val payload: String? = null,
	val createdAt: LocalDateTime? = null
) {
	fun toAuditLog(): AuditLog {
		val log = AuditLog()
		log.action = action
		log.actorId = actorId
		log.target = target
		log.payload = payload
		log.createdAt = createdAt ?: LocalDateTime.now()
		return log
	}
}
