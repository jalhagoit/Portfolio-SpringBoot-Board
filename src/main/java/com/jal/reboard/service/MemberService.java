package com.jal.reboard.service;

import com.jal.reboard.domain.dto.MemberDTO;
import com.jal.reboard.domain.dto.MemberInfoDTO;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.RoleType;
import com.jal.reboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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
        Member member = memberRepository.findMnoByUsername(username);

        if(passwordEncoder.matches(password, member.getPassword())){
            String encodePassword = passwordEncoder.encode(newPwd);
            member.setPassword(encodePassword);
            memberRepository.save(member);
            return true;
        } else {
            //            throw new RuntimeException("현재 비밀번호와 일치하지 않습니다");
            return false;
        }

    }

}
