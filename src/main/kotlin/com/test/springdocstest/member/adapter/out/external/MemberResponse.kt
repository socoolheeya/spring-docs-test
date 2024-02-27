package com.test.springdocstest.member.adapter.out.external

class MemberResponse {
    companion object {
        data class Search(
            val memberId: Long,
            val name: String,
            val email: String,
            val isDelete: Boolean
        )

        data class Modify(
            val memberId: Long,
            val name: String,
            val email: String,
            val isDelete: Boolean
        )

        data class Register(
            val memberId: Long,
            val name: String,
            val password: String,
            val email: String,
            val isDelete: Boolean
        )
    }
}