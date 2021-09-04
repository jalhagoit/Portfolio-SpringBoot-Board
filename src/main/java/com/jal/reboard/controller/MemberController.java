package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.BoardRfDTO;
import com.jal.reboard.domain.dto.MemberInfoDTO;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.service.MemberService;
import com.jal.reboard.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    PaginationService paginationService;

    @GetMapping("/register")
    public String registerForm() {
        return "member/registerForm";
    }

    @PostMapping("/register")
    public String registerPost(Member member) {
        memberService.회원가입(member);
        return "member/registerConfirmPage";
//        return "checkEmailForRegister";

    }

    // Login form
    @GetMapping("/login")
    public String login() {
        return "member/loginForm";
    }

    // Login form with error
    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "member/loginForm";
    }

    /* 회원 페이지 */
//    @PreAuthorize("isAuthenticated()") // 작동 안하는데???
    @GetMapping("/mem")
    public String memInfo(HttpServletRequest httpServletRequest, Model model) {
        String username = httpServletRequest.getUserPrincipal().getName();
        MemberInfoDTO dto = memberService.회원정보조회(username);
        model.addAttribute("regDate", dto.getRegDate());

        return "member/memberInfo";
    }

    /* 회원 작성글 페이지*/
    @GetMapping("/mem/boards")
    public String memBoards(HttpServletRequest httpServletRequest, Model model,
                            @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
                            // direction 적용이 안 돼. DESC/ASC 둘 다 오름차순으로 출력됨.
        String username = httpServletRequest.getUserPrincipal().getName();
        BoardRfDTO dto = paginationService.회원작성글페이지(username, pageable);
        model.addAttribute("boards", dto.getFindall());
        model.addAttribute("pageNums", dto.getPagination());
        return "member/memberBoards";
    }

}
