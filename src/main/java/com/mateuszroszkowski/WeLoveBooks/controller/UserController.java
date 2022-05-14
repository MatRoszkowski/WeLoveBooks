package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.security.CurrentUser;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import com.mateuszroszkowski.WeLoveBooks.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username){
        return userService.getUserDtoByUsername(username);
    }

    @PatchMapping("")
    public UserDTO updateUser(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserDTO userDTO){
        return userService.updateUser(userPrincipal.getId(), userDTO);
    }

    @DeleteMapping("")
    public void deleteUser(@CurrentUser UserPrincipal userPrincipal){
        userService.deleteUser(userPrincipal.getId());
    }

    @GetMapping("/search")
    public List<UserDTO> searchUser(@RequestParam String q) {
        return userService.searchUserDtoByUsername(q);
    }

    @GetMapping("/logged/info")
    public UserDTO getLoggedUserInfo(@CurrentUser UserPrincipal user) {
        return userService.getUserDtoById(user.getId());
    }
}
