package com.jal.reboard.service;

import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardRepository boardRepository;

    /* 작성자 조회 */
    @Test
    @Transactional(readOnly = true)
    public void 작성자조회() {
        Board board = boardRepository.findById(2L)
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패 : 아이디를 찾을 수 없습니다.");
                });
        System.out.println("작성자: " +board.getMember().getUsername());
    }
}
