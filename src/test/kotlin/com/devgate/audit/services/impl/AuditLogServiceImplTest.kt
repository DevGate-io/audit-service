package com.devgate.audit.services.impl

import com.devgate.audit.models.AuditLog
import com.devgate.audit.repositories.AuditLogRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.willThrow
import org.mockito.Mockito.mock
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.Optional

class AuditLogServiceImplTest {

	private lateinit var repository: AuditLogRepository
	private lateinit var service: AuditLogServiceImpl

	@BeforeEach
	fun setUp() {
		repository = mock(AuditLogRepository::class.java)
		service = AuditLogServiceImpl(repository)
	}

	private fun logAt(id: Long, createdAt: LocalDateTime): AuditLog {
		return AuditLog().apply {
			this.id = id
			this.createdAt = createdAt
		}
	}

	// ---------- getLogs ----------

	@Test
	fun `getLogs returns logs sorted by createdAt descending`() {
		val older = logAt(1, LocalDateTime.of(2026, 1, 1, 10, 0))
		val newest = logAt(2, LocalDateTime.of(2026, 6, 1, 10, 0))
		val middle = logAt(3, LocalDateTime.of(2026, 3, 1, 10, 0))
		given(repository.findAll()).willReturn(listOf(older, newest, middle))

		val result = service.getLogs()

		assertThat(result).containsExactly(newest, middle, older)
	}

	@Test
	fun `getLogs returns empty list when repository is empty`() {
		given(repository.findAll()).willReturn(emptyList())

		assertThat(service.getLogs()).isEmpty()
	}

	// ---------- getLogById ----------

	@Test
	fun `getLogById returns log when found`() {
		val log = logAt(42, LocalDateTime.now())
		given(repository.findById(42L)).willReturn(Optional.of(log))

		assertThat(service.getLogById(42L)).isSameAs(log)
	}

	@Test
	fun `getLogById throws 404 when not found`() {
		given(repository.findById(99L)).willReturn(Optional.empty())

		val ex = assertThrows(ResponseStatusException::class.java) { service.getLogById(99L) }

		assertThat(ex.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
		then(repository).should().findById(99L)
	}

	// ---------- saveLog ----------

	@Test
	fun `saveLog delegates to repository`() {
		val log = logAt(1, LocalDateTime.now())

		service.saveLog(log)

		then(repository).should().save(log)
	}

	// ---------- deleteLog ----------

	@Test
	fun `deleteLog delegates to repository`() {
		service.deleteLog(7L)

		then(repository).should().deleteById(7L)
	}

	@Test
	fun `deleteLog throws 404 when repository fails`() {
		willThrow(EmptyResultDataAccessException(1)).given(repository).deleteById(123L)

		val ex = assertThrows(ResponseStatusException::class.java) { service.deleteLog(123L) }

		assertThat(ex.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
	}
}
