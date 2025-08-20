
package com.back.domain.member.member.repository

import com.back.domain.member.member.entity.Member
import com.back.domain.member.member.entity.QMember
import com.back.standard.dto.MemberSearchKeywordType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils


class MemberRepositoryImpl(private val queryFactory : JPAQueryFactory ) : MemberRepositoryCustom {
    override fun findQById(id: Int): Member? {
        val member = QMember.member

        return queryFactory
            .selectFrom(member)
            .where(member.id.eq(id))
            .fetchOne()
    }

    override fun findQByUsername(username: String): Member? {
        val member = QMember.member

        return queryFactory
            .selectFrom(member)
            .where(member.username.eq(username))
            .fetchOne()
    }

    override fun findQByIdIn(ids: List<Int>): List<Member> {
        val member = QMember.member

        return queryFactory
            .selectFrom(member)
            .where(member.id.`in`(ids))
            .fetch()
    }

    override fun findQByUsernameAndEitherPasswordOrNickname(
        username: String,
        password: String?,
        nickname: String?
    ): List<Member> {
        val member = QMember.member

        return queryFactory
            .selectFrom(member)
            .where(
                member.username.eq(username)
                    .and(
                        member.password.eq(password)
                            .or(member.nickname.eq(nickname))
                    )
            )
            .fetch()
    }

    override fun findQByNicknameContaining(nickname: String): List<Member> {
        val member = QMember.member

        return queryFactory
            .selectFrom(member)
            .where(member.nickname.contains(nickname))
            .fetch()
    }

    override fun existsQByNicknameContaining(nickname: String): Boolean {
        val member = QMember.member

        return queryFactory
            .selectOne()
            .from(member)
            .where(member.nickname.contains(nickname))
            .fetchFirst() != null
    }

    override fun findQPagedByKw(
        kw: String,
        pageable: Pageable
    ): Page<Member> {
        val member = QMember.member

        // 조건 빌더 생성
        val builder = com.querydsl.core.BooleanBuilder()
        if (kw.isNotBlank()) {
            builder.and(member.nickname.contains(kw))
        }

        // 기본 query 생성
        val query = queryFactory
            .selectFrom(member)
            .where(builder)

        // pageable 정렬 조건 적용
        pageable.sort.forEach { order ->
            when (order.property) {
                "id" -> query.orderBy(if (order.isAscending) member.id.asc() else member.id.desc())
                "username" -> query.orderBy(if (order.isAscending) member.username.asc() else member.username.desc())
                "nickname" -> query.orderBy(if (order.isAscending) member.nickname.asc() else member.nickname.desc())
            }
        }

        // 페이징 데이터 조회
        val results = query
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        // 카운트 쿼리 (조건 동일하게 적용)
        val totalQuery = queryFactory
            .select(member.count())
            .from(member)
            .where(builder)

        return PageableExecutionUtils.getPage(results, pageable) {
            totalQuery.fetchFirst() ?: 0L
        }
    }

    override fun findQPagedByKwAndKwType(kw: String, kwType: MemberSearchKeywordType, pageable: Pageable): Page<Member> {
        val member = QMember.member

        // 조건 빌더 생성
        val builder = com.querydsl.core.BooleanBuilder()
        if (kw.isNotBlank()) {
            when (kwType) {

                MemberSearchKeywordType.USERNAME -> builder.and(member.username.contains(kw))
                MemberSearchKeywordType.NICKNAME -> builder.and(member.nickname.contains(kw))
                MemberSearchKeywordType.ALL -> builder.and(
                    member.username.contains(kw)
                        .or(member.nickname.contains(kw))
                )
            }
        }

        // 기본 query 생성
        val query = queryFactory
            .selectFrom(member)
            .where(builder)

        // pageable 정렬 조건 적용
        pageable.sort.forEach { order ->
            when (order.property) {
                "id" -> query.orderBy(if (order.isAscending) member.id.asc() else member.id.desc())
                "username" -> query.orderBy(if (order.isAscending) member.username.asc() else member.username.desc())
                "nickname" -> query.orderBy(if (order.isAscending) member.nickname.asc() else member.nickname.desc())
            }
        }

        // 페이징 데이터 조회
        val results = query
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        // 카운트 쿼리 (조건 동일하게 적용)
        val totalQuery = queryFactory
            .select(member.count())
            .from(member)
            .where(builder)

        return PageableExecutionUtils.getPage(results, pageable) {
            totalQuery.fetchFirst() ?: 0L
        }
    }
}
