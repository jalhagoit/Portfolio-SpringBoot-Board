package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.BoardListResponseDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.service.BoardService;
import com.jal.reboard.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BaordApiControllerTest {

    @Autowired
    BoardService boardService;

    @Autowired
    PaginationService paginationService;

//    @GetMapping("/test/list")
//    public String list(Model model, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Board> boards = paginationService.페이지번호(pageable);
//        model.addAttribute("boards", boards);
//        return "boards";
//    }

    @GetMapping("/test/list")
    public List<Integer> list(Model model, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
        return paginationService.페이지번호(pageable);
    }

    @GetMapping("/test/list/json")
    public Page<Board> listjson(Model model, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.글목록조회(pageable);
    }

    @GetMapping("/test/list/new")
    public List<BoardListResponseDTO> findAll() {
        return boardService.findAllDesc();
    }

//    @PostMapping("/test/write")
//    public Board writePost(Board board) {
////        boardService.글작성(board);
////        return "redirect:/list";
//        return boardService.글작성(board);
//    }
}
