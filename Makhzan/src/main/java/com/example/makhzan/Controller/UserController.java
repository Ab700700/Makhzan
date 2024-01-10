package com.example.makhzan.Controller;

import com.example.makhzan.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/makhzan/user")
public class UserController {


    @PostMapping("/login")
    public ResponseEntity login(){
        return ResponseEntity.status(HttpStatus.OK).body("Logged in successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
    }

}
