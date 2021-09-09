package com.jal.reboard.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ReplyDTO {

    private Long rno;
    private String content;

    private String username;

    private Long bno;
    private LocalDateTime creDate;
}
