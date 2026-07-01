package com.devgate.audit.services.impl

import com.devgate.audit.models.AuditLog
import com.devgate.audit.repositories.AuditLogRepository
import com.devgate.audit.services.AuditLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuditLogServiceImpl @Autowired constructor(
	private val logRepository: AuditLogRepository
): AuditLogService {
	override fun getLogs(): List<AuditLog> {
		return logRepository.findAll().sortedByDescending() { it.createdAt }
	}

	override fun getLogById(id: Long): AuditLog {
		return logRepository.findById(id).orElseThrow {
			ResponseStatusException(HttpStatus.NOT_FOUND)
		}
	}

	override fun saveLog(log: AuditLog) {
		logRepository.save(log)
	}

	override fun deleteLog(id: Long) {
		try {
			logRepository.deleteById(id)
		} catch (e: Exception) {
			throw ResponseStatusException(HttpStatus.NOT_FOUND)
		}
	}
}
