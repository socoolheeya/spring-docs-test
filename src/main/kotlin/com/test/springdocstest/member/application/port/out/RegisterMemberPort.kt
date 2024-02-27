package com.test.springdocstest.member.application.port.out

import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface RegisterMemberPort {
    fun registerMember(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register
}