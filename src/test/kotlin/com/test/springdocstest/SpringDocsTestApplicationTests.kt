package com.test.springdocstest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.test.springdocstest.member.adapter.out.external.MemberDto
import com.test.springdocstest.member.adapter.out.external.MemberRequest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SpringDocsTestApplicationTests {

    @Test
    fun contextLoads() {
        val registerMember = MemberRequest.Companion.Register(
            name = "tester_1",
            email = "tester_1@gmail.com",
            password = "1234",
            isDelete = false
        )

        val str = jacksonObjectMapper().writeValueAsString(registerMember)
        println(str)

        val ttt = registerMember.toString()
        println("ttt : $ttt")

        val dto = MemberDto(
            name = "tester_1",
            email = "tester_1@gmail.com",
            isDelete = false,
            memberId = null
        )

        val dtoStr = jacksonObjectMapper().writeValueAsString(dto)
        println(dtoStr)
    }

}
