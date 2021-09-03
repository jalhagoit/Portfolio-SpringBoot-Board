package com.jal.reboard.service;

import com.jal.reboard.domain.dto.BoardListResponseDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

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

    /* 제목 검색 */
//    @Test
//    @Transactional(readOnly = true)
//    public void 제목검색() {
//        List<Board> boardList = boardRepository.findByTitleLike("%제목%");
//
//        IntStream.rangeClosed(0,boardList.size()-1).forEach(i->{
//            System.out.println(boardList.get(i).getTitle());
//        });
//
////        return boardList;
//    }

}
