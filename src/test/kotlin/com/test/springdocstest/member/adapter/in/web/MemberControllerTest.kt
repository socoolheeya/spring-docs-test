package com.test.springdocstest.member.adapter.`in`.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.test.springdocstest.configuration.RestDocsConfiguration
import com.test.springdocstest.member.adapter.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse
import com.test.springdocstest.member.application.service.MemberService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter


@AutoConfigureRestDocs
@Import(RestDocsConfiguration::class)
@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(controllers = [MemberController::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberControllerTest(
    private var mockMvc: MockMvc,
    private val restDocs: RestDocumentationResultHandler,
    @MockBean val memberService: MemberService
) {

    @BeforeEach
    fun setUp(context: WebApplicationContext, provider: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(provider))
            .alwaysDo<DefaultMockMvcBuilder>(restDocs)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    private fun <T> anyLong(): T {
        Mockito.any<T>()
        return 0L as T
    }

    @Test
    @DisplayName("멤버 조회")
    fun load() {
        val response = MemberResponse.Companion.Search(
            memberId = 1L,
            name = "hong",
            email = "hong@gmail.com",
            isDelete = false
        )

        BDDMockito.given(memberService.load(anyLong()))
            .willReturn(response)
        mockMvc.perform(RestDocumentationRequestBuilders.get("/members/{memberId}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            print()
        ).andDo(restDocs.document(
                pathParameters(
                    parameterWithName("memberId").description("멤버 ID")
                ),
                responseFields(
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 ID"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("isDelete").type(JsonFieldType.BOOLEAN).description("삭제여부")
                )
            ))
            .andExpect(content().json("{\"memberId\" : 1, \"name\" : \"hong\", " +
                    "\"email\" : \"hong@gmail.com\", \"isDelete\" :  false}"))
    }


    @Test
    @DisplayName("멤버 등록")
    fun register() {

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
        ).andDo(restDocs.document(
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                fieldWithPath("isDelete").type(JsonFieldType.BOOLEAN).description("삭제여부")
            ),
            responseFields(
                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 ID"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                fieldWithPath("isDelete").type(JsonFieldType.BOOLEAN).description("삭제여부")
            )
        ),
        ).andExpect(status().isOk())
            .andReturn()
    }

    @Test
    @DisplayName("멤버 수정")
    fun modify() {
        val response = MemberResponse.Companion.Modify(
            memberId = 1,
            name = "tester",
            email = "tester@gmail.com",
            isDelete = false
        )

        val modifyMember = MemberRequest.Companion.Modify(
            memberId = 1,
            name = "tester",
            email = "tester@gmail.com",
            isDelete = false
        )

        BDDMockito.given(memberService.modify(anyLong(), any()))
            .willReturn(response)

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/members/{memberId}", 1)
            .content(jacksonObjectMapper().writeValueAsString(modifyMember))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            print()
        ).andDo(restDocs.document(
            pathParameters(
                parameterWithName("memberId").description("멤버 ID")
            ),
            requestFields(
                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 ID"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                fieldWithPath("isDelete").type(JsonFieldType.BOOLEAN).description("삭제여부")
            ),
            responseFields(
                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 ID"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("isDelete").type(JsonFieldType.BOOLEAN).description("삭제여부")
            )
        )
        ).andExpect(status().isOk())
            .andReturn()
    }

    @Test
    @DisplayName("멤버 삭제")
    fun remove() {
        BDDMockito.doNothing()
            .`when`(memberService)
            .remove(anyLong())


        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/members/{memberId}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
            print()
        ).andDo(restDocs.document(
            pathParameters(
                parameterWithName("memberId").description("멤버 ID")
            ),
        )
        ).andExpect(status().isOk())
    }
}