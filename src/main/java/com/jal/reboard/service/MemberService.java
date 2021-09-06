package com.jal.reboard.service;

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
    public void 회원가입(Member member) {
        String encodePassword = passwordEncoder.encode(member.getPassword());

        member.setRoleType(RoleType.USER);
        member.setPassword(encodePassword);

        memberRepository.save(member);
    }


    /* 로그인 */

    /* 회원 조회 */
    public MemberInfoDTO 회원정보조회(String username) {
        Member member = memberRepository.findMnoByUsername(username);
        MemberInfoDTO dto = MemberInfoDTO.builder()
                .mno(member.getMno())
                .username(member.getUsername())
                .regDate(member.getRegDate())
                .build();

        return dto;
    }



}
