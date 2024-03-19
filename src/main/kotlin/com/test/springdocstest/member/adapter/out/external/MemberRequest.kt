package com.test.springdocstest.member.adapter.out.external

import com.fasterxml.jackson.annotation.JsonInclude
import com.test.springdocstest.member.domain.Member
import jakarta.validation.constraints.NotNull

open class MemberRequest {
    companion object {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        data class Register(
            private val name: String,
            private val email: String?,
            private val password: String,
            private val isDelete: Boolean
        ) {
            fun toEntity(): Member {
                return Member(
                    memberId = null,
                    name = name,
                    email = email,
                    password = password,
                    isDelete = isDelete
                )
            }

            fun getName(): String = name
            fun getEmail(): String? = email
            fun getPassword(): String = password
            fun isDelete(): Boolean = isDelete
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        data class Modify(
            @NotNull
            private var memberId: Long,
            @NotNull
            private val name: String,
            private val email: String?,
            @NotNull
            private val isDelete: Boolean
        ) {
            fun toEntity(): Member {
                return Member(
                    memberId = memberId,
                    name = name,
                    email = email,
                    password = "",
                    isDelete = isDelete
                )
            }

            fun getMemberId(): Long = memberId
            fun getName(): String = name
            fun getEmail(): String? = email
            fun isDelete(): Boolean = isDelete
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        data class ModifyPassword(
            private var memberId: Long,
            private val password: String
        ) {
            fun getMemberId(): Long = memberId
            fun getPassword(): String = password
        }

        data class Remove(
            val memberId: Long
        )
    }
}