package com.example.controllers;

import com.example.dto.UserDto;
import com.example.dto.UsernamePasswordValidationRequest;
import com.example.services.RegisterService;
import com.example.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService service;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @RequestBody @Valid RegisterRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/validate-users")
    public HttpStatus validateEmailAndPasswordForStudents(@RequestBody @Valid UsernamePasswordValidationRequest request) {
        userService.findByUsername(request.getUsername());
        return HttpStatus.OK;
    }

}
