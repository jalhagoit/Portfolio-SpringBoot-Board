package com.jal.reboard.controller;

import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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