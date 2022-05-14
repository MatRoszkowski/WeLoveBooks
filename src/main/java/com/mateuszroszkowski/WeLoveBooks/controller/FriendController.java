package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.security.CurrentUser;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import com.mateuszroszkowski.WeLoveBooks.service.FriendService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "http://localhost:3000")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/{friendId}")
    public UserDTO sendFriendInvitation(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long friendId) {
        return friendService.sendFriendRequest(userPrincipal.getId(), friendId);
    }

    @GetMapping("/hasInvitation")
    public Boolean hasUserAnyInvitation(@CurrentUser UserPrincipal userPrincipal) {
        return friendService.hasAnyInvitation(userPrincipal.getId());
    }

    @GetMapping("/{friendId}/hasInvitation")
    public Boolean hasUserInvitationFromFriend(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long friendId) {
        return friendService.hasUserInvitation(userPrincipal.getId(), friendId);
    }

    @GetMapping("/{friendId}/hasSentInvitation")
    public Boolean hasUserSentInvitation(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long friendId) {
        return friendService.hasUserSentInvitation(userPrincipal.getId(), friendId);
    }

    @GetMapping("/{friendId}/areFriends")
    public Boolean areUsersFriends(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long friendId) {
        return friendService.areUsersFriends(userPrincipal.getId(), friendId);
    }

    @DeleteMapping("/deleteFriend/{friendId}")
    public void deleteFriend(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long friendId){
        friendService.deleteFriend(userPrincipal.getId(), friendId);
    }

    @GetMapping("/getUserFriends/{friendId}")
    public List<UserDTO> getUserFriends(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long friendId){
        return friendService.getUserFriends(userPrincipal.getId(), friendId);
    }

    @GetMapping("/invitations")
    public List<UserDTO> getUserInvitations(@CurrentUser UserPrincipal userPrincipal) {
        return friendService.getUserInvitations(userPrincipal.getId());
    }

    @DeleteMapping("/invitations/{friendId}")
    public void deleteInvitation(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long friendId) {
        friendService.deleteInvitation(userPrincipal.getId(), friendId);
    }
}
