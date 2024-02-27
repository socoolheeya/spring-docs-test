package com.test.springdocstest.member.application.port.`in`

import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface LoadMemberUseCase {
    fun load(memberId: Long): MemberResponse.Companion.Search
}