package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.UserMapper;
import com.mateuszroszkowski.WeLoveBooks.model.User;
import com.mateuszroszkowski.WeLoveBooks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getUserDtoById(Long id){
        User user = getUserById(id);
        return userMapper.map(user);
    }

    public User getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public void deleteUser(Long id){
        User user = getUserById(id);
        user.getFavouriteBooks().forEach(book -> book.getFavouriteUsers().remove(user));
        user.getEvents().forEach(event -> event.getUsers().remove(user));
        user.getBooks().forEach(book -> book.getUsers().remove(user));
        user.getGroups().forEach(group -> group.getUsers().remove(user));
        userRepository.delete(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        User user = getUserById(id);
        if(userDTO.getUsername()!=null) {
            user.setUsername(userDTO.getUsername());
        }
        if(userDTO.getPassword()!=null) {
            user.setPassword(userDTO.getPassword());
        }
        if(userDTO.getRoles().isEmpty()) {
            user.setRoles(user.getRoles());
        }

        return userMapper.map(userRepository.save(user));
    }

    public UserDTO getUserDtoByUsername(String username) {
        return userMapper.map(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<UserDTO> searchUserDtoByUsername(String username) {
        return userRepository
                .findByUsernameIgnoreCaseContaining(username)
                .stream()
                .map(user -> userMapper.map(user))
                .collect(Collectors.toList());
    }
}
