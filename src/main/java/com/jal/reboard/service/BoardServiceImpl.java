package com.jal.reboard.service;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.CType;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.repository.MemberRepository;
import com.jal.reboard.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReplyRepository replyRepository;

    /* 글 작성 */
    public Long writeBoard(BoardDTO dto) {
        Member member = memberRepository.findMnoByUsername(dto.getUsername());

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .ctype(CType.ONBOARD)
                .build();

        boardRepository.save(board);

        return board.getBno();
    }


    /* 작성자 일치 확인 */
    @Transactional(readOnly = true)
    public boolean checkSameWriter(Long bno, HttpServletRequest httpServletRequest) {
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
    public Board viewBoard(Long bno) {
        return boardRepository.findById(bno)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    /* 글 삭제 */
    @Transactional
    public void deleteBoard(Long bno) {
        replyRepository.deleteByBnoDepth2(bno);
        replyRepository.deleteByBnoDepth1(bno);
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }


    /* 글 수정 */
    @Transactional
    public void modifyBoard(BoardDTO boardDTO) {

        Board board = boardRepository.getById(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);
    }

}
