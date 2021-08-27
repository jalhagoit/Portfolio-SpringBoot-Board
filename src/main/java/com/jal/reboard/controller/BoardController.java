package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.service.BoardService;
import com.jal.reboard.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    @GetMapping("/")
    public String test(){
        return "index";
    }


    @Autowired
    BoardService boardService;

    @Autowired
    PaginationService paginationService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> boards = boardService.글목록조회(pageable);
        List<Integer> pageNums = paginationService.페이지번호(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("pageNums", pageNums);
        return "boards";
    }

    @GetMapping("/write")
    public String writeForm(){
        return "write";
    }

    @PostMapping("/write")
    public String writePost(Board board) {
        boardService.글작성(board);
        return "redirect:/list";
//        return "redirect:/board/{bno}";
    }

    @GetMapping("/board/{bno}")
    public String findById(@PathVariable Long bno, Model model) {
        model.addAttribute("board", boardService.글상세보기(bno));

        return "detail";
    }

    @DeleteMapping("/board/{bno}")
    public String removeBoard(@PathVariable Long bno){
        boardService.글삭제(bno);
        return "redirect:/list";
    }

    @GetMapping("/board/modify/{bno}")
    public String modifyForm(@PathVariable Long bno, Model model) {
        model.addAttribute("board", boardService.글상세보기(bno));
        return "modify";
    }

    @PutMapping("/board/modify/{bno}") //post로도 수정 되는데???
    public String modifyBoard(BoardDTO dto) {
        boardService.글수정(dto);
        return "redirect:/board/{bno}";
    }

}