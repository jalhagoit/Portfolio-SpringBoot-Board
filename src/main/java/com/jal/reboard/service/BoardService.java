package com.jal.reboard.service;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.type.CType;
import com.jal.reboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    /* 글 작성 */
    public void 글작성(Board board) {
        board.setCtype(CType.ONBOARD);
        boardRepository.save(board);
    }

    /* 전체 글 조회 */
    public List<Board> 글목록조회() {
        return boardRepository.findAll();
    }

//    public Board 글하나조회(Long boardId) {
//        return boardRepository.findOne(boardId);
//    }

    /* 글 수정 */
    @Transactional
    public void modify(BoardDTO dto) {

        Board board = boardRepository.getById(dto.getBno());

        board.changeTitle(dto.getTitle());
        board.changeContent(dto.getContent());

        boardRepository.save(board);
    }
}
