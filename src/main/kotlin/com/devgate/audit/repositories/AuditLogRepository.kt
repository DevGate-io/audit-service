package com.devgate.audit.repositories

import com.devgate.audit.models.AuditLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuditLogRepository: JpaRepository<AuditLog, Long>