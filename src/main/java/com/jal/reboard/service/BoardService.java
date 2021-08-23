package com.jal.reboard.service;

import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    /* 글 작성 */
    public void 글작성(Board board) {
        boardRepository.save(board);
    }

    /* 전체 글 조회 */
    public List<Board> 글목록조회() {
        return boardRepository.findAll();
    }

//    public Board 글하나조회(Long boardId) {
//        return boardRepository.findOne(boardId);
//    }
}
