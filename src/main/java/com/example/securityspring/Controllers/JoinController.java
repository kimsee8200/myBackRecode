package com.example.securityspring.Controllers;

import com.example.securityspring.Services.JoinService;
import com.example.securityspring.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.*;

@Controller
public class JoinController {
    @Autowired
    private JoinService joinService;
    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(UserDTO user){

        System.out.println(user);
        System.out.println(user.getUsername());
        joinService.joinProcess(user);

        return "redirect:/login";
    }
}
