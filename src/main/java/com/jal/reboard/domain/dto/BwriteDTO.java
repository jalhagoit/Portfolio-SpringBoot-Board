package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.CType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class BwriteDTO {

    private Long bno;
    private String title;
    private String content;

    private LocalDateTime creDate;
    private LocalDateTime modDate;

    private CType ctype;

    private Member mno;

    private String username;


}
