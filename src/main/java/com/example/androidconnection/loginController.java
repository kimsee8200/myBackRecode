package com.example.androidconnection;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class loginController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto user){
        return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
    }
}
