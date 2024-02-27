package com.test.springdocstest.member.adapter.out.external

import com.test.springdocstest.member.domain.Member

data class MemberDto(
    var memberId: Long?,
    var name: String,
    var email: String?,
    var isDelete: Boolean
) {
    companion object {

        fun toEntity(memberDto: MemberDto): Member {
            return Member(
                memberId = memberDto.memberId,
                name = memberDto.name,
                password = "",
                email = memberDto.email,
                isDelete = memberDto.isDelete
            )
        }
    }
}