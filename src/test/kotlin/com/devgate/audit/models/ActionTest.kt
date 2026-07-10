package com.devgate.audit.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ActionTest {

	@Test
	fun `toValue returns the domain string`() {
		assertThat(Action.SERVICE_CREATED.toValue()).isEqualTo("service.created")
		assertThat(Action.ROLE_CHANGED.toValue()).isEqualTo("user.role_changed")
		assertThat(Action.TEAM_MEMBER_ADDED.toValue()).isEqualTo("team.member.added")
	}

	@Test
	fun `every action has a non-blank and unique value`() {
		val values = Action.entries.map { it.value }

		assertThat(values).doesNotHaveDuplicates()
		assertThat(values).allSatisfy { assertThat(it).isNotBlank() }
	}

	@Test
	fun `valueOf throws for an unknown name`() {
		assertThrows(IllegalArgumentException::class.java) {
			Action.valueOf("NOT_A_REAL_ACTION")
		}
	}
}
