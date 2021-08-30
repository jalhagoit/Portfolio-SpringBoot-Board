package com.jal.reboard.service;

import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberRepository memberRepository;

    /* 회원 조회 */
    @Test
    @Transactional(readOnly = true)
    public void 회원조회() {
        Member member = memberRepository.findById(2L)
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패 : 아이디를 찾을 수 없습니다.");
                });
        System.out.println("유저네임: " +member.getUsername());
    }

    /* 회원의 작성글 조회 */
    @Test
    @Transactional(readOnly = true)
    public void 작성글조회() {
        Member member = memberRepository.findById(1L)
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패 : 아이디를 찾을 수 없습니다.");
                });
        System.out.println("작성글: " +member.getBoards().get(0).getTitle());
    }
}
