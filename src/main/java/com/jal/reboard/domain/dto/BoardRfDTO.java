package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class BoardRfDTO {

    private Page<Board> findall;
    private List<Integer> pagination;

    public BoardRfDTO(Page<Board> findall, List<Integer> pagination) {
        this.findall = findall;
        this.pagination = pagination;
    }

}
