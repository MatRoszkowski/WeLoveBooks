package com.mateuszroszkowski.WeLoveBooks.dto.mapper;

import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDTO> {
    @Override
    public UserDTO map(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()))
                .build();
    }
}
