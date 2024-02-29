package com.test.springdocstest.member.domain

import com.test.springdocstest.member.adapter.out.external.MemberDto
import com.test.springdocstest.member.adapter.out.external.MemberResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.springframework.transaction.annotation.Transactional


@Entity
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET isDelete = true WHERE memberId = ?")
class Member(
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberId: Long?,
    @Column
    var name: String,
    @Column
    var email: String?,
    @Column
    var password: String,
    @Column
    var isDelete: Boolean = false
) {
    constructor(): this(
        0L, "", null, "", false
    )

    @Transactional
    fun update(memberDto: MemberDto) {
        this.name = memberDto.name
        this.email = memberDto.email
        this.isDelete = memberDto.isDelete
    }

    @Transactional
    fun updatePassword(password: String) {
        this.password = password
    }

    fun toDomain(): MemberDto {
        return MemberDto(
            memberId = memberId,
            name = name,
            email = email,
            isDelete = isDelete
        )
    }

    fun toResponseSearchDomain(): MemberResponse.Companion.Search {
        return MemberResponse.Companion.Search(
            memberId = memberId?: 0L,
            name = name,
            email = email?: "default@gmail.com",
            isDelete = isDelete
        )
    }

    fun toResponseRegisterDomain(): MemberResponse.Companion.Register {
        return MemberResponse.Companion.Register(
            memberId = memberId?: 0L,
            name = name,
            email = email?: "default@gmail.com",
            password = password,
            isDelete = isDelete
        )
    }

    fun toResponseModifyDomain(): MemberResponse.Companion.Modify {
        return MemberResponse.Companion.Modify(
            memberId = memberId?: 0L,
            name = name,
            email = email?: "default@gmail.com",
            isDelete = isDelete
        )
    }

}