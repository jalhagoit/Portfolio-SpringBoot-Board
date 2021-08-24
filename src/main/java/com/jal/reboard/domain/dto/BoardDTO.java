package com.jal.reboard.domain.dto;

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
}
