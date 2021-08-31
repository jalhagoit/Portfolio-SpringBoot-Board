package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.CType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;

    private LocalDateTime creDate;
    private LocalDateTime modDate;

    private CType ctype;

    private Member member;
}
