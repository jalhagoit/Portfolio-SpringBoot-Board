package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.dto.BoardRfDTO;
import com.jal.reboard.domain.dto.BwriteDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.service.BoardService;
import com.jal.reboard.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BoardController {

//    @GetMapping("/")
//    public String test(){
//        return "index";
//    }


    @Autowired
    BoardService boardService;

    @Autowired
    PaginationService paginationService;

    /* 전체 글목록 조회 */
    @GetMapping({"/", "/list"})
    public String boardListAll(Model model, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {

        BoardRfDTO dto = paginationService.페이지(pageable);
        model.addAttribute("boards", dto.getFindall());
        model.addAttribute("pageNums", dto.getPagination());

        return "board/boards";
    }

    /* 제목 검색 */
    @GetMapping("/list/search")
    public String searchTitlle(String keyword, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable ,Model model) {

        BoardRfDTO dto = paginationService.제목검색페이지("%"+keyword+"%", pageable);
        model.addAttribute("keyword", keyword);
        model.addAttribute("boards", dto.getFindall());
        model.addAttribute("pageNums", dto.getPagination());

        return "board/srchBoards";
    }

    /* 글 작성 페이지 */
    @PreAuthorize("isAuthenticated()") // 로그인 해야 해당 페이지로 넘어간다.
    @GetMapping("/write")
    public String writeForm(){
        return "board/write";
    }

    /* 글 작성 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writePost(Board board, BwriteDTO dto, RedirectAttributes redirectAttributes) {
        boardService.글작성(board, dto);
        redirectAttributes.addAttribute("bno", board.getBno());
        return "redirect:/board/{bno}";
    }

    /* 글 상세보기 */
    @GetMapping("/board/{bno}")
    public String findById(@PathVariable Long bno, Model model) {
        model.addAttribute("board", boardService.글상세보기(bno));

        return "board/detail";
    }

    /* 글 삭제 */
    @DeleteMapping("/board/{bno}")
    public String removeBoard(HttpServletRequest httpServletRequest, @PathVariable Long bno){
        if (boardService.작성자일치확인(bno, httpServletRequest)) {
            boardService.글삭제(bno);
            return "redirect:/list";
        }
        return "/board/{bno}";
    }

    /* 글 수정 페이지 */
    @GetMapping("/board/modify/{bno}")
    public String modifyForm(HttpServletRequest httpServletRequest, @PathVariable Long bno, Model model) {
        if (boardService.작성자일치확인(bno, httpServletRequest)) {
            model.addAttribute("board", boardService.글상세보기(bno));
            return "board/modify";
        } return "redirect:/list";
    }

    /* 글 수정 */
    @PutMapping("/board/modify/{bno}") //post로도 수정 되는데???
    public String modifyBoard(BoardDTO dto) {
        boardService.글수정(dto);
        return "redirect:/board/{bno}";
    }

}