package com.example.pagination.salwa.controllers;

import com.example.pagination.salwa.dto.PaginationResponse;
import com.example.pagination.salwa.models.User;
import com.example.pagination.salwa.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService us ;

    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<User>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(us.getUsers(page,size));
    }
}
