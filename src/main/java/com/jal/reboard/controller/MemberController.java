package com.jal.reboard.controller;

import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

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

}
