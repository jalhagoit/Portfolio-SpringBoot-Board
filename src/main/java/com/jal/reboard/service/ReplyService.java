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
}
