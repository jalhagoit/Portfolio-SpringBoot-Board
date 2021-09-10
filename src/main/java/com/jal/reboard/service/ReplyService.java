package com.jal.reboard.service;

import com.jal.reboard.domain.dto.ReplyDTO;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.entity.Reply;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.repository.MemberRepository;
import com.jal.reboard.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class ReplyService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyRepository replyRepository;

//    @Transactional
    public ReplyDTO 댓글작성(ReplyDTO dto) {
        Member mno = memberRepository.findMnoByUsername(dto.getUsername());

        Reply reply = Reply.builder()
                .board(boardRepository.getById(dto.getBno())) // 글
                .member(mno) // 댓글 작성자
                .content(dto.getContent())
                .build();

        Reply reply1 = replyRepository.save(reply);

        ReplyDTO dto1 = ReplyDTO.builder()
                .rno(reply1.getRno())
                .bno(reply1.getBoard().getBno())
                .content(reply1.getContent())
                .username(reply1.getMember().getUsername())
                .creDate(reply1.getCreDate())
                .build();

        return dto1;
    }

    @Transactional(readOnly = true)
    public boolean 작성자일치확인(Long rno, HttpServletRequest httpServletRequest) {
        String writer = replyRepository.findById(rno)
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 조회 실패 : rno를 찾을 수 없습니다.");
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

    @Transactional
    public void 댓글삭제(Long rno) {
        replyRepository.deleteById(rno);
    }
}
