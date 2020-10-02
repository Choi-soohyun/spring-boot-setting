package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // 이 상태(HashMap)로 하면 실무에서는 동시성 문제가 발생할 수 있다. 그래서 공유 되는 변수일 때는 ConcurrentHashMap을 쓰는게 맞다.
    private static Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // null이 반환 될 가능성이 있으면, Optional.ofNullable()로 감쌀 때, 클라이언트에서 작업할 수 있게 된다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 루프로 돌린다.
                .filter(member -> member.getName().equals(name)) // 루프를 돌려서 getName과 매개변수 name이 같은지 확인한 후
                .findAny(); // 하나라도 찾으면 반환한다. 끝까지 찾을 경우 반환할 게 없으면(null), Optional로 감싸져서 반환이 된다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // list로 형변환이 되는구나 와.. 엄청 간단하네 ㅋㅋ () 안에가 저런 역할을 하는 거였구나.
    }

    // 설명4)
    public void clearStore() {
        store.clear();
    }
}
