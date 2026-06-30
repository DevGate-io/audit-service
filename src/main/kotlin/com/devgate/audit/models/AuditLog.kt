package com.devgate.audit.models

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "audit_events")
class AuditLog {
	@Id
	@GeneratedValue
	var id: Long? = null

	@Column(name = "action")
	@Enumerated(EnumType.STRING)
	var action: Action? = null

	@Column(name = "actor_id")
	var actorId: UUID? = null

	@Embedded
	var target: Target? = null

	@Column(name = "payload")
	var payload: String? = null

	@Column(name = "created_at")
	var createdAt: LocalDateTime = LocalDateTime.now()

	override fun toString(): String {
		return "AuditLog(id=$id, action=$action, actorId=$actorId, target=$target, payload=$payload, createdAt=$createdAt)"
	}
}

@Embeddable
class Target {
	@Column(name = "target_type")
	var type: String? = null

	@Column(name = "target_id")
	var id: String? = null

	@Column(name = "target_label")
	var label: String? = null

	override fun toString(): String {
		return "Target(type=$type, id=$id, label=$label)"
	}
}
