package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*; // 오 Import도 static이 가능하네.
// static으로 선언시에는 assertThat을 바로 사용할 수 있다.

// 다른데에서 사용할 것이 아니니까 public 지워주자.
// Test method가 많을 경우에 class에 있는 run을 해주거나, package를 잡아서 Tests를 할 수도 있다.
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 설명3) 매 메소드마다 실행 후 이 메소드를 실행 시켜주는 작업
    @AfterEach
    public void afterEach() {
        // 설명5) 설명4를 보다시피 clear 해주는 메소드를 MemoryMemberRepository 클래스에 추가하였다. 그럼 우린 이제 그걸 실행만 시켜주면 된다.
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get(); // Optional에서 값을 바로 꺼낼때 단, 테스트일때만 get()으로 꺼내본다.

        // save한 것과 findById 한 것이 동일하면 ~!
//        System.out.println("result = " + (result == member)); // 글자로 내가 계속 볼 수 없으니 다음 assertions로 해보자.
        Assertions.assertEquals(member, result);
//        Assertions.assertEquals(member, null); // 이걸로 테스트해보면, 아 위에가 정상이구나, 아니구나를 알 수 있다.

        // 아래는 member가 result랑 똑같다. 라고 읽기가 좀 더 편한다.
        org.assertj.core.api.Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("name1");
        repository.save(member1);

        Member member2 = new Member(); // shortCut :: Shift + F6하면 동일한 문구 선택
        member2.setName("name2");
        repository.save(member2);

        Member result = repository.findByName("name1").get();

        assertThat(result).isEqualTo(member1); // 성공, member2로 변경하면 당연히 오류
    }


    // 설명1) 위 각각의 메서드를 테스트 했을 때는 오류가 나지 않다가 아래 findAll 포함해서 전체 패키지 test 시에 오류가 난다. 무조건
    // 이유인즉, Test Method의 테스트 실행순서는 보장이 되지 않는다.
    // 근데 그것보다 우선은 findByName 메서드와, findAll 메서드가 동일한 네임을 줄 경우 이미 중복되었기 때문에 findAll 또는 findByName 메서드가 오류난다.
    // 그러니, 한번에 Test 시에는 모든 method는 따로 동작을 할 수 있게끔 만들어주어야 한다.
    @Test
    public void findAll() {
        // 설명2) 아래처럼 그냥 하면 다른 메서드와 동일한 이름을 set 하기 때문에 에러나고,
        // set한 내용을 Clear를 시켜준 다음 아래를 진행하면 오류가 나지 않는다. 설명3은 해당 파일 맨 위에 있다.
        // ★ 테스트 실행이 되고 끝날 때마다 저장소를 지운다. 테스트는 순서와 관계없이, 의존 관계없이 진행되게끔 해야한다.
        Member member1 = new Member();
        member1.setName("name1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("name2");
        repository.save(member2);


        List<Member> result = repository.findAll();
 
        assertThat(result.size()).isEqualTo(2); // 숫자를 변경하면 오류
    }

    /*
    * 테스트 수업이 종료 후 + 추가 Tip)
    *
    * 이번 수업처럼 먼저 개발 작업을 한 후 Test를 할 수도 있고,
    * Test 클래스를 작성하고 개발 작업을 할 수도 있다. 이걸 테스트 주도 개발이라 하고 이 것을 TDD라고 한다.
    * 테스트 코드 없이 개발할 경우에는 나중에 문제가 정말 많아질테니 꼭 테스트 코드 작성하면서 진행하자.
    * */
}
