package com.jal.reboard.service;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.dto.BwriteDTO;
import com.jal.reboard.domain.entity.Board;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {

    Long writeBoard(BwriteDTO dto);

    boolean checkSameWriter(Long bno, HttpServletRequest httpServletRequest);

    Board viewBoard(Long bno);

    void deleteBoard(Long bno);

    void modifyBoard(BoardDTO boardDTO);


}
