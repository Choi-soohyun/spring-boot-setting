package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member Save(Member member);
    Optional<Member> findById(Long id); // Optional : Java8부터 사용할수 있다. null을 반환하지 않고, Optional로 감싸서 반환한다.
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
