package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.ResetPasswordDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.security.CurrentUser;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import com.mateuszroszkowski.WeLoveBooks.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO){
        return authService.login(userDTO);
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO userDTO){
        return authService.createUser(userDTO);
    }

    @PatchMapping("/reset")
    public UserDTO resetPassword(@CurrentUser UserPrincipal user, @RequestBody ResetPasswordDTO passwordDTO) {
        return authService.resetPassword(user.getId(), passwordDTO);
    }
}
