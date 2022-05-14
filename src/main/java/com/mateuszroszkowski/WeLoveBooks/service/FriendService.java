package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.UserMapper;
import com.mateuszroszkowski.WeLoveBooks.model.User;
import com.mateuszroszkowski.WeLoveBooks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public FriendService(UserRepository userRepository, UserService userService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public UserDTO sendFriendRequest(Long userId, Long friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);

        if (!hasUserInvitation(userId, friendId) &&
                !hasUserSentInvitation(userId, friendId) &&
                !areUsersFriends(userId, friendId)) {
            friend.getFriendRequests().add(user);
        } else if (hasUserInvitation(userId, friendId)) {
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            userRepository.save(friend);
            user.getFriendRequests().clear();
        } else if (hasUserSentInvitation(userId, friendId)) {
            throw new RuntimeException("Can't send invitation. User has already sent the invitation");
        } else {
            throw new RuntimeException("Can't send invitation. Users are already friends");
        }

        return userMapper.map(userRepository.save(user));
    }

    public Boolean hasUserInvitation(Long userId, Long friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);
        return user.getFriendRequests().contains(friend);
    }

    public Boolean hasUserSentInvitation(Long userId, Long friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);
        return friend.getFriendRequests().contains(user);
    }

    public Boolean areUsersFriends(Long userId, Long friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);
        return user.getFriends().contains(friend);
    }

    public List<UserDTO> getUserFriends(Long id, Long userId) {
        User user = userService.getUserById(userId);
        List<UserDTO> friendsDTO = new ArrayList<>();
        Set<User> friends = user.getFriends();
        friends.forEach(friend -> friendsDTO.add(userMapper.map(friend)));
        return friendsDTO;
    }

    public void deleteFriend(Long userId, Long friendId) {
        if (areUsersFriends(userId, friendId)) {
            User user = userService.getUserById(userId);
            User friend = userService.getUserById(friendId);

            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
            userRepository.save(friend);
            userRepository.save(user);
        }
    }

    public boolean hasAnyInvitation(Long userId) {
        return userService.getUserById(userId).getFriendRequests().size() > 0;
    }

    public List<UserDTO> getUserInvitations(Long userId) {
        return userService.getUserById(userId).getFriendRequests()
                .stream()
                .map(request -> userMapper.map(request))
                .collect(Collectors.toList());
    }

    public void deleteInvitation(Long userId, Long friendId) {
        User user = userService.getUserById(userId);
        user.getFriendRequests().remove(userService.getUserById(friendId));
        userRepository.save(user);
    }
}
