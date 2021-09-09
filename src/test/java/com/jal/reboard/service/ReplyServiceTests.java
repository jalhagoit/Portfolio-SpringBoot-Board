package com.jal.reboard.service;

import com.jal.reboard.domain.entity.Reply;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.repository.MemberRepository;
import com.jal.reboard.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Transactional 달면 생성이 되는데 db에 없어.. 왜지??
    public void 댓글달기() {

//        Reply reply = Reply.builder()
//                            .board(boardRepository.getById(1L)) // 글
//                            .member(memberRepository.getById(1L)) // 댓글 작성자
//                            .content("댓글c...test")
//                            .build();
//
//        replyRepository.save(reply);

        IntStream.rangeClosed(11,100).forEach(i -> {

            long bno = (long)(Math.random() * 225) +1;
            if (boardRepository.existsById(bno)) {

                long mno = (long)(Math.random() * 39) +1;
                if (memberRepository.existsById(mno)) {

                    Reply reply = Reply.builder()
                            .board(boardRepository.getById(bno)) // 글
                            .member(memberRepository.getById(mno)) // 댓글 작성자
                            .content("댓글c..." + i)
                            .build();

                    replyRepository.save(reply);

                }
            }
        });
    }
}
