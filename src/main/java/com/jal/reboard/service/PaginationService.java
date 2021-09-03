package com.jal.reboard.service;

import com.jal.reboard.domain.dto.BoardRfDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    @Autowired
    BoardRepository boardRepository;

    /* 페이지 번호 계산 */
    public BoardRfDTO 페이지번호(Pageable pageable) {

        Page<Board> fa = boardRepository.findAll(pageable); // 여기서 조회된 것을 이용하는 듯.(쿼리문을 더 생성하지 않는다.)
        int presentPage = fa.getNumber();
        int totalPage = fa.getTotalPages();

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

        List<Integer> pagination = IntStream.rangeClosed(prev, next).boxed().collect(Collectors.toList());

        return new BoardRfDTO(fa, pagination);
    }

    /* 페이지 번호 계산 */
    public List<Integer> 제목검색페이지번호(String title, Pageable pageable) {

        Page<Board> ft = boardRepository.findByTitleLike(title, pageable); // 여기서 조회된 것을 이용하는 듯.(쿼리문을 더 생성하지 않는다.)
        int presentPage = ft.getNumber();
        int totalPage = ft.getTotalPages();

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
