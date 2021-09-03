package com.jal.reboard.service;

import com.jal.reboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Integer> 페이지번호(Pageable pageable) {

        int presentPage = boardRepository.findAll(pageable).getNumber();
        int totalPage = boardRepository.findAll(pageable).getTotalPages();

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

    /* 페이지 번호 계산 */
    public List<Integer> 제목검색페이지번호(String title, Pageable pageable) {

        int presentPage = boardRepository.findByTitleLike(title, pageable).getNumber();
        int totalPage = boardRepository.findByTitleLike(title, pageable).getTotalPages();

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
