package com.back.domain.member.member.repository


import com.back.domain.member.member.entity.Member
import com.back.standard.dto.MemberSearchKeywordType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MemberRepositoryCustom {
    fun findQById(id : Int) : Member?
    fun findQByUsername(username: String): Member?
    fun findQByIdIn(ids: List<Int>): List<Member>
    fun findQByUsernameAndEitherPasswordOrNickname(username: String, password: String?, nickname: String?): List<Member>
    fun findQByNicknameContaining(nickname: String): List<Member>
    fun existsQByNicknameContaining(nickname: String): Boolean
    fun findQPagedByKw(kw: String, pageable: Pageable): Page<Member>
    fun findQPagedByKwAndKwType(kw: String, kwType: MemberSearchKeywordType, pageable: Pageable): Page<Member>

}
