package com.test.springdocstest.member.adapter.out.persistence

import com.test.springdocstest.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long>