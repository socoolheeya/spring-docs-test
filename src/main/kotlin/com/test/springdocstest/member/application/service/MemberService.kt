package com.test.springdocstest.member.application.service

import com.test.springdocstest.member.adapter.out.external.MemberDto
import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse
import com.test.springdocstest.member.application.port.`in`.LoadMemberUseCase
import com.test.springdocstest.member.application.port.`in`.ModifyMemberUseCase
import com.test.springdocstest.member.application.port.`in`.RegisterMemberUseCase
import com.test.springdocstest.member.application.port.`in`.RemoveMemberUseCase
import com.test.springdocstest.member.application.port.out.LoadMemberPort
import com.test.springdocstest.member.application.port.out.ModifyMemberPort
import com.test.springdocstest.member.application.port.out.RegisterMemberPort
import com.test.springdocstest.member.application.port.out.RemoveMemberPort
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val loadMemberPort: LoadMemberPort,
    private val registerMemberPort: RegisterMemberPort,
    private val modifyMemberPort: ModifyMemberPort,
    private val removeMemberPort: RemoveMemberPort
): LoadMemberUseCase, RegisterMemberUseCase, ModifyMemberUseCase, RemoveMemberUseCase {

    override fun load(memberId: Long): MemberResponse.Companion.Search {
        return loadMemberPort.loadMember(memberId)
    }

    override fun register(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register {
        return registerMemberPort.registerMember(request)
    }

    override fun modify(memberId: Long, request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify {

        return modifyMemberPort.modifyMember(request)
    }

    override fun remove(memberId: Long) {
        removeMemberPort.removeMember(memberId)
    }
}