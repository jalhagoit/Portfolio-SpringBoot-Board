package com.jal.reboard.domain.dto;

import com.jal.reboard.domain.entity.Board;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class BoardListDTO {

    private Page<Board> findall;
    private List<Integer> pagination;

    public BoardListDTO(Page<Board> findall, List<Integer> pagination) {
        this.findall = findall;
        this.pagination = pagination;
    }

}
