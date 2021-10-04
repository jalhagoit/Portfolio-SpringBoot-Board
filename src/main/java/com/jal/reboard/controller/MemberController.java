package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.BoardListDTO;
import com.jal.reboard.domain.dto.MemberDTO;
import com.jal.reboard.domain.dto.MemberInfoDTO;
import com.jal.reboard.service.MemberService;
import com.jal.reboard.service.PaginationService;
import com.jal.reboard.service.RegexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    PaginationService paginationService;

    @Autowired
    RegexService regexService;

    /* 회원 가입 */
    @GetMapping("/register")
    public String registerForm() {
        return "member/registerForm";
    }

    @PostMapping("/register")
    public String registerPost(String username, String password, Model model, MemberDTO dto) {
        if (regexService.usernamePattern(username)
                && regexService.passwordPattern(password)) {

            if (memberService.usernameDuplicationCheck(username)) {
                model.addAttribute("msg", "이미 존재하는 Username입니다.");
                return "member/registerForm";
            } else {
                memberService.register(dto);
                return "member/registerConfirmPage";
                // TODO return "checkEmailForRegister";
            }

        } else {
            model.addAttribute("msg", "Username 또는 Password가 양식에 맞지 않습니다.");
            return "member/registerForm";
        }

    }

    /* 로그인 페이지 */
    @GetMapping("/login")
    public String login() {
        return "member/loginForm";
    }

    /* 로그인 에러 페이지 */
    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "member/loginForm";
    }

    /* 회원 페이지 */
    @GetMapping("/mem")
    public String memInfo(HttpServletRequest httpServletRequest, Model model) {
        String username = httpServletRequest.getUserPrincipal().getName();
        MemberInfoDTO dto = memberService.memberInfo(username);
        model.addAttribute("regDate", dto.getRegDate());

        return "member/memberInfo";
    }

    @GetMapping("/mem/changeInfo")
    public String changePwdForm() {
        return "member/changePwd";
    }

    @PutMapping("/mem/changeInfo")
    public String changePwd(HttpServletRequest httpServletRequest, String password, String newPwd, RedirectAttributes redirectAttributes, Model model) {
        if (regexService.passwordPattern(newPwd)) {
            String username = httpServletRequest.getUserPrincipal().getName();
            if (memberService.changePwd(username, password, newPwd)) {
                redirectAttributes.addFlashAttribute("msg", true);
                return "redirect:/mem";
            } else {
                model.addAttribute("msg", "입력하신 비밀번호가 기존 비밀번호와 일치하지 않습니다.");
                return "member/changePwd";
            }

        } else {
            model.addAttribute("msg", "입력하신 비밀번호가 양식에 맞지 않습니다.");
            return "member/changePwd";
        }
    }


    /* 회원 작성글 페이지*/
    @GetMapping("/mem/boards")
    public String memBoards(HttpServletRequest httpServletRequest, Model model,
                            @PageableDefault(sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
        String username = httpServletRequest.getUserPrincipal().getName();
        BoardListDTO dto = paginationService.memberWrittenBoardList(username, pageable);
        model.addAttribute("boards", dto.getFindall());
        model.addAttribute("pageNums", dto.getPagination());
        return "member/memberBoards";
    }

    /* 회원 탈퇴 */
    @DeleteMapping("/mem/account")
    public String deleteAccount(HttpServletRequest httpServletRequest, String password, RedirectAttributes redirectAttributes) throws ServletException {

        String username = httpServletRequest.getUserPrincipal().getName();

        if (memberService.checkPasswordMatch(username, password)) {
            memberService.deleteAccount(username);
            httpServletRequest.logout();
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("pwdMsg", true);
            return "redirect:/mem";
        }

    }

}
