package com.jal.reboard.controller;

import com.jal.reboard.domain.dto.ReplyDTO;
import com.jal.reboard.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @PostMapping("/replyWrite/{bno}")
    public String replyWrite(HttpServletRequest httpServletRequest, ReplyDTO dto) {
        dto.setUsername(httpServletRequest.getUserPrincipal().getName());
        replyService.댓글작성(dto);
        return "redirect:/board/{bno}";
    }

}
