package com.test.springdocstest.member.application.port.out

import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface ModifyMemberPort {
    fun modifyMember(request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify
}