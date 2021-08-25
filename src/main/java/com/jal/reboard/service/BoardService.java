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

    /* 글 조회 */
    @Transactional(readOnly = true)
    public Board 글상세보기(Long bno) {
        return boardRepository.findById(bno)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    /* 글 삭제 */
    @Transactional
    public void 글삭제(Long bno) {
        boardRepository.deleteById(bno);
    }


    /* 글 수정 */
    @Transactional
    public void 글수정(BoardDTO boardDTO) {

        Board board = boardRepository.getById(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);
    }
}
