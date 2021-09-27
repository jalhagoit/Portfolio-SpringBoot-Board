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

    /* 댓 작성 */
    public void replyWrite(ReplyDTO dto) {
        Member mno = memberRepository.findMnoByUsername(dto.getUsername());

        Reply reply = Reply.builder()
                .board(boardRepository.getById(dto.getBno())) // 글
                .member(mno) // 댓글 작성자
                .content(dto.getContent())
                .ctype(CType.ONBOARD)
                .delDate(null)
                .depth(0)
                .build();

        replyRepository.save(reply);
    }

    /* 대댓 작성 */
    public void reReplyWrite(ReplyDTO dto) {
        Member mno = memberRepository.findMnoByUsername(dto.getUsername());

        if (dto.getParent().getCtype() == CType.ONBOARD && dto.getParent().getDepth() < 2) {
            Reply reply = Reply.builder()
                    .board(boardRepository.getById(dto.getBno())) // 글
                    .member(mno) // 댓글 작성자
                    .content(dto.getContent())
                    .ctype(CType.ONBOARD)
                    .delDate(null)
                    .parent(dto.getParent())
                    .depth(dto.getParent().getDepth()+1)
                    .build();

            replyRepository.save(reply);
        }
    }

    /* 댓글 작성자 일치 확인 */
    @Transactional(readOnly = true)
    public boolean checkSameWriter(Long rno, HttpServletRequest httpServletRequest) {
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

    /* 댓글 삭제 */
    @Transactional
    public void replyDelete(Long rno) {
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
