package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.type.RoleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class MemberDTO {

    private Long mno;
    private String username;
    private String password;

//    private String email;

    private RoleType roleType;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
//    private LocalDateTime delDate;

}
