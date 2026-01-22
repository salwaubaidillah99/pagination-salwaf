package com.example.pagination.salwa.controllers;

import com.example.pagination.salwa.dto.PaginationResponse;
import com.example.pagination.salwa.models.User;
import com.example.pagination.salwa.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users API", description = "API for retrieving paginated users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService us ;

    public UserController(UserService us) {
        this.us = us;
    }

    @Operation(summary = "Get paginated users")
    @GetMapping
    public ResponseEntity<PaginationResponse<User>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(us.getUsers(page,size));
    }
}
