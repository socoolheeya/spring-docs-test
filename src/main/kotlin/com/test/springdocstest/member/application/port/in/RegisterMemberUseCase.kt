package com.test.springdocstest.member.application.port.`in`

import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface RegisterMemberUseCase {
    fun register(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register
}