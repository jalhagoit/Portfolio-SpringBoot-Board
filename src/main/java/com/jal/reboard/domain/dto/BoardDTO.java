package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.type.CType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;

    private LocalDateTime creDate;
    private LocalDateTime modDate;

    private CType ctype;
}
