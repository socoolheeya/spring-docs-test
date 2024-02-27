package com.test.springdocstest.member.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse
import com.test.springdocstest.member.application.service.MemberService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.snippet.Attributes
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.charset.StandardCharsets


@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(controllers = [MemberController::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberControllerTest(
    private val mockMvc: MockMvc,
    @MockBean val memberService: MemberService
) {

    @BeforeEach
    fun setUp() {

    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    @Test
    fun makeRestDocs() {
        this.mockMvc.perform(
            MockMvcRequestBuilders.get("/sample/api/url").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andDo(print())
            .andDo(document("sample-docs"))
    }

    @Test
    @DisplayName("멤버 조회")
    fun loadMember() {

        mockMvc.perform(MockMvcRequestBuilders.get("/members/{memberId}", 1))
            .andDo(MockMvcResultHandlers.print())
            .andDo(document("members/load",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 ID"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                    fieldWithPath("isDelete").type(JsonFieldType.BOOLEAN).description("삭제여부")
                )
            ))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"memberId\" : 1, \"name\" : \"hong\", " +
                    "\"email\" : \"hong@gmail.com\", \"password\" : \"1234\", \"isDelete\" :  false}"))
    }


    @Test
    @DisplayName("멤버 등록")
    fun registerMember() {

        val response = MemberResponse.Companion.Register(
            memberId = 1L,
            name = "hong",
            password = "1234",
            email = "hong@gmail.com",
            isDelete = false
        )

        BDDMockito.given(memberService.register(any()))
            .willReturn(response)


        val registerMember = MemberRequest.Companion.Register(
            name = "tester",
            email = "tester@gmail.com",
            password = "1234",
            isDelete = false
        )

        this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
            .content(jacksonObjectMapper().writeValueAsString(registerMember))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            print()
        ).andDo(document("members/register",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()))
        ).andExpect(status().isOk())
            .andReturn()
    }
}