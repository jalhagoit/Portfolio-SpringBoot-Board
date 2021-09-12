package com.jal.reboard.service;

import com.jal.reboard.domain.dto.ReplyDTO;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.entity.Reply;
import com.jal.reboard.domain.type.CType;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.repository.MemberRepository;
import com.jal.reboard.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class ReplyService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyRepository replyRepository;

//    @Transactional
    public void 댓글작성(ReplyDTO dto) {
        Member mno = memberRepository.findMnoByUsername(dto.getUsername());

        Reply reply = Reply.builder()
                .board(boardRepository.getById(dto.getBno())) // 글
                .member(mno) // 댓글 작성자
                .content(dto.getContent())
                .ctype(CType.ONBOARD)
                .delDate(null)
                .parent(Reply.builder().rno(0L).build())
                .build();

        replyRepository.save(reply);
    }

    public void 대댓작성(ReplyDTO dto) {
        Member mno = memberRepository.findMnoByUsername(dto.getUsername());

        Reply reply = Reply.builder()
                .board(boardRepository.getById(dto.getBno())) // 글
                .member(mno) // 댓글 작성자
                .content(dto.getContent())
                .ctype(CType.ONBOARD)
                .delDate(null)
                .parent(dto.getParent())
                .build();

        replyRepository.save(reply);
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
        if (replyRepository.findByParentLike(Reply.builder().rno(rno).build()).size() > 0) {

            Reply reply = replyRepository.getById(rno);
            reply.setCtype(CType.DELETED);
            reply.setDelDate(LocalDateTime.now());

            replyRepository.save(reply);

        } else {
            replyRepository.deleteById(rno);
        }
    }
}
