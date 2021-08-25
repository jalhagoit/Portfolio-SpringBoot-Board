package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardService.글목록조회();
        model.addAttribute("boards", boards);
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


//    @PostMapping(value = "/write", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String writePostJson(@RequestBody Board board) {
//        boardService.글작성(board);
//        return "redirect:/list";
//    }

//    @PostMapping(value = "/write", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public String writePostForm(Board board) {
//        boardService.글작성(board);
//        return "redirect:/list";
//    }



}