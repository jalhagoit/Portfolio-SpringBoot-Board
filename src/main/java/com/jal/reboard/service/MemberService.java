package com.jal.reboard.service;

import com.jal.reboard.domain.dto.MemberDTO;
import com.jal.reboard.domain.dto.MemberInfoDTO;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.RoleType;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.repository.MemberRepository;
import com.jal.reboard.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    /* 회원가입 */
    @Transactional
    public void register(MemberDTO dto) {
        String encodePassword = passwordEncoder.encode(dto.getPassword());

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(encodePassword)
                .roleType(RoleType.USER)
                .build();

        memberRepository.save(member);
    }

    /* username 중복 확인*/
    @Transactional(readOnly = true)
    public boolean usernameDuplicationCheck(String username) {
        return memberRepository.existsByUsername(username);
    }


    /* 회원 조회 */
    public MemberInfoDTO memberInfo(String username) {
        Member member = memberRepository.findMnoByUsername(username);
        MemberInfoDTO dto = MemberInfoDTO.builder()
                .mno(member.getMno())
                .username(member.getUsername())
                .regDate(member.getRegDate())
                .build();

        return dto;
    }

    /* 비밀번호 변경 */
    public boolean changePwd(String username, String password, String newPwd) {

        if(checkPasswordMatch(username, password)){
            Member member = memberRepository.findMnoByUsername(username);
            String encodePassword = passwordEncoder.encode(newPwd);
            member.setPassword(encodePassword);
            memberRepository.save(member);
            return true;
        } else {
            // TODO throw new RuntimeException("현재 비밀번호와 일치하지 않습니다");
            return false;
        }

    }

    /* 비번 일치 확인 */
    public boolean checkPasswordMatch(String username, String password) {
        Member member = memberRepository.findMnoByUsername(username);

        return passwordEncoder.matches(password, member.getPassword());
    }

    /* 회원 탈퇴 */
    @Transactional
    public void deleteAccount(String username) {

        Member member = memberRepository.findMnoByUsername(username);

        List<Long> bnos = boardRepository.findBnoByMember(member);
        if (bnos.size() > 0) { // null이 아니라면 (작성글이 있다면)
            for (Long bno : bnos) {
                // 작성글들을 모두 삭제한다
                boardService.deleteBoard(bno);
            }
        }

        List<Long> rnos = replyRepository.findRnoByMember(member);
        if (rnos.size() > 0) { // null이 아니라면 (작성댓이 있다면)
            for (Long rno : rnos) {
                // 작성댓들을 모두 삭제한다
                replyService.deleteReply(rno);
            }
        }

        memberRepository.deleteByUsername(username);
    }

}
