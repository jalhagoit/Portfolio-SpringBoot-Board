package com.jal.reboard.service;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.dto.BoardListResponseDTO;
import com.jal.reboard.domain.dto.BwriteDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.CType;
import com.jal.reboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    /* 글 작성 */
    public void 글작성(Board board, BwriteDTO dto) {
        Member mno = boardRepository.findMnoByUsername(dto.getUsername());
        board.setMember(mno);
        board.setCtype(CType.ONBOARD);
        boardRepository.save(board);
    }


    /* @Query로 원하는 필드들만 조회하려는데 ConverterNotFoundException이 뜬다 */
    @Transactional(readOnly = true)
    public List<BoardListResponseDTO> findAllDesc() {
        return boardRepository.findAllDesc().stream()
                .map(BoardListResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean 작성자일치확인(Long bno, HttpServletRequest httpServletRequest) {
        String writer = boardRepository.findById(bno)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : bno를 찾을 수 없습니다.");
                })
                .getMember().getUsername();

        try {
            String username = httpServletRequest.getUserPrincipal().getName();
            return writer.equals(username);
        } catch (NullPointerException e) {
            System.out.println("사용자가 로그인을 하지 않았습니다.");
            return false;
        } catch (Exception e) {
            System.out.println("무슨 예외야?");
            e.printStackTrace();
            return false;
        }
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


//    /* 작성자 추가 */
//    @Transactional
//    public void 작성자추가(BoardDTO boardDTO) {
//
//        Board board = boardRepository.getById(boardDTO.getBno());
//
//        board.changeWriter(boardDTO.getWriter());
//
//        boardRepository.save(board);
//    }
}
