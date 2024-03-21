package com.example.securityspring;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/chuser")
    public ResponseEntity<?> provide(UserDTO user){
        return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
    }
}
