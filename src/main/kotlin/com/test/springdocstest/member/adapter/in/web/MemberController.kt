package com.test.springdocstest.member.adapter.`in`.web

import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse
import com.test.springdocstest.member.application.service.MemberService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members", produces = [MediaType.APPLICATION_JSON_VALUE])
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/{memberId}")
    fun getMember(@PathVariable memberId: Long): MemberResponse.Companion.Search? =
        memberService.load(memberId)

    @PostMapping("")
    fun register(@RequestBody request: MemberRequest.Companion.Register): MemberResponse.Companion.Register {
       return memberService.register(request)
    }

    @PutMapping("/{memberId}")
    fun modify(@PathVariable memberId: Long, @RequestBody request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify {
        return memberService.modify(memberId, request)
    }

    @DeleteMapping("/{memberId}")
    fun remove(@PathVariable memberId: Long) {
        memberService.remove(memberId)
    }
}