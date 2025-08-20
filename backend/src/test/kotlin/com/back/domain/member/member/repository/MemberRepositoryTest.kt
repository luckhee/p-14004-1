package com.back.domain.member.member.repository

import com.back.standard.extensions.getOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberRepositoryTest {
    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("findById")
    fun t1() {
        val member = memberRepository.findById(1).orElseThrow()

        assertThat(member.id).isEqualTo(1)
    }

    @Test
    @DisplayName("findByUsername")
    fun t3() {
        val member = memberRepository.findByUsername("admin").getOrThrow()

        assertThat(member.username).isEqualTo("admin")
    }

    @Test
    @DisplayName("findQByUsername")
    fun t4() {
        val member = memberRepository.findQByUsername("admin").getOrThrow()

        assertThat(member.username).isEqualTo("admin")
    }

    @Test
    @DisplayName("findByIdIn")
    fun t5() {
        val members = memberRepository.findByIdIn(listOf(1, 2, 3))

        assertThat(members).isNotEmpty
        assertThat(members.map { it.id }).containsAnyOf(1, 2, 3)
    }

    @Test
    @DisplayName("findQByIdIn")
    fun t6() {
        val members = memberRepository.findQByIdIn(listOf(1, 2, 3))

        assertThat(members).isNotEmpty
        assertThat(members.map { it.id }).containsAnyOf(1, 2, 3)
    }

    @Test
    @DisplayName("findByNicknameContaining")
    fun t13() {
        val members = memberRepository.findByNicknameContaining("유저")

        assertThat(members).isNotEmpty
        assertThat(members.all { it.nickname.contains("유저") }).isTrue
    }

    @Test
    @DisplayName("findQByNicknameContaining")
    fun t14() {
        val members = memberRepository.findQByNicknameContaining("유저")

        assertThat(members).isNotEmpty
        assertThat(members.all { it.nickname.contains("유저") }).isTrue
    }
}
