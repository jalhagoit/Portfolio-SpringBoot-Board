package com.jal.reboard.service;

import com.jal.reboard.domain.dto.BoardRfDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public BoardRfDTO boardListAll(Pageable pageable) {

        Page<Board> boards = boardRepository.findAll(pageable);

        return new BoardRfDTO(boards, pagination(boards));
    }

    @Transactional(readOnly = true)
    public BoardRfDTO searchList(String searchType, String keyword, Pageable pageable) {

        Page<Board> findBoard = null;
        
        if (searchType.equals("t")) {
            findBoard = boardRepository.findByTitleLike(keyword, pageable);
        } else if (searchType.equals("c")) {
            findBoard = boardRepository.findByContentLike(keyword, pageable);
        } else if (searchType.equals("tc")) {
            findBoard = boardRepository.findByTitleLikeOrContentLike(keyword, keyword, pageable);
        }

        return new BoardRfDTO(findBoard, pagination(findBoard));
    }

    @Transactional(readOnly = true)
    public BoardRfDTO memberWrittenBoardList(String username, Pageable pageable) {

        Member member = memberRepository.findMnoByUsername(username);
        Page<Board> page = boardRepository.findBnoByMember(member, pageable);

        return new BoardRfDTO(page, pagination(page));
    }



    /* 페이지 번호 계산 */
    public List<Integer> pagination(Page<Board> findBoard ) {

        int presentPage = findBoard.getNumber();
        int totalPage = findBoard.getTotalPages();

        int prev, next;

        if (totalPage < 5) {
            prev = 0;
            next = totalPage - 1;

        } else {

            if (presentPage < 5) {
                prev = 0;
                next = 4;
            } else {
                prev = (presentPage/5) * 5;
                if (prev + 4 >= totalPage){
                    next = totalPage - 1;
                } else {
                    next = prev + 4;
                }
            }

        }

        return IntStream.rangeClosed(prev, next).boxed().collect(Collectors.toList());

    }

}
