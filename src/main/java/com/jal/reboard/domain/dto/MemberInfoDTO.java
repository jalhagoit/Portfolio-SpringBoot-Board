package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class MemberInfoDTO {
    private Long mno;
    private String username;
//    private String password;

//    private String email;

//    private RoleType roleType;

    private LocalDateTime regDate; // 가입일
    private LocalDateTime modDate; // 정보 수정일


}
