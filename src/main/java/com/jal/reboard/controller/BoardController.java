package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.BoardListDTO;
import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.service.BoardServiceImpl;
import com.jal.reboard.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BoardController {


    @Autowired
    BoardServiceImpl boardService;

    @Autowired
    PaginationService paginationService;

    /* 전체 글목록 조회 */
    @GetMapping({"/", "/list"})
    public String boardListAll(Model model, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {

        BoardListDTO dto = paginationService.boardListAll(pageable);
        model.addAttribute("boards", dto.getFindall());
        model.addAttribute("pageNums", dto.getPagination());

        return "board/boards";
    }

    /* 검색 */
    @GetMapping("/list/search")
    public String searchBoards(String keyword, String searchType, @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable ,Model model) {

        BoardListDTO dto = paginationService.searchList(searchType, "%"+keyword+"%", pageable);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("boards", dto.getFindall());
        model.addAttribute("pageNums", dto.getPagination());

        return "board/srchBoards";
    }

    /* 글 작성 페이지 */
    @GetMapping("/write")
    public String writeForm(){
        return "board/write";
    }

    /* 글 작성 */
    @PostMapping("/write")
    public String writePost(BoardDTO dto, RedirectAttributes redirectAttributes) {
        Long bno = boardService.writeBoard(dto);
        redirectAttributes.addAttribute("bno", bno);
        return "redirect:/board/{bno}";
    }

    /* 글 상세보기 */
    @GetMapping("/board/{bno}")
    public String findById(@PathVariable Long bno, Model model) {
        model.addAttribute("board", boardService.viewBoard(bno));

        return "board/detail";
    }

    /* 글 삭제 */
    @DeleteMapping("/board/{bno}")
    public String removeBoard(HttpServletRequest httpServletRequest, @PathVariable Long bno){
        if (boardService.checkSameWriter(bno, httpServletRequest)) {
            boardService.deleteBoard(bno);
            return "redirect:/list";
        }
        return "/board/{bno}";
    }

    /* 글 수정 페이지 */
    @GetMapping("/board/modify/{bno}")
    public String modifyForm(HttpServletRequest httpServletRequest, @PathVariable Long bno, Model model) {
        if (boardService.checkSameWriter(bno, httpServletRequest)) {
            model.addAttribute("board", boardService.viewBoard(bno));
            return "board/modify";
        } return "redirect:/list";
    }

    /* 글 수정 */
    @PutMapping("/board/modify/{bno}")
    public String modifyBoard(BoardDTO dto) {
        boardService.modifyBoard(dto);
        return "redirect:/board/{bno}";
    }

}