package com.test.springdocstest.member.adapter.out.external

import com.fasterxml.jackson.annotation.JsonInclude
import com.test.springdocstest.member.domain.Member

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
            private var memberId: Long,
            private val name: String,
            private val email: String?,
            private val password: String,
            private val isDelete: Boolean
        ) {
            fun toEntity(): Member {
                return Member(
                    memberId = memberId,
                    name = name,
                    email = email,
                    password = password,
                    isDelete = isDelete
                )
            }

            fun getMemberId(): Long = memberId
            fun getName(): String = name
            fun getEmail(): String? = email
            fun getPassword(): String = password
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