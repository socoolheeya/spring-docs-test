package com.test.springdocstest.member.application.port.`in`

import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface ModifyMemberUseCase {
    fun modify(memberId: Long, request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify
}