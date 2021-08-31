package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResponseDTO {

    private Long bno;
    private String title;

    private LocalDateTime modDate;
    private Long mno;


    public BoardListResponseDTO(Board entity) {
        this.bno = entity.getBno();
        this.title = entity.getTitle();
        this.modDate = entity.getModDate();
        this.mno = entity.getMember().getMno();
    }
}
