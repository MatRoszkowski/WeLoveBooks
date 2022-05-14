package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.ResetPasswordDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.UserMapper;
import com.mateuszroszkowski.WeLoveBooks.model.Role;
import com.mateuszroszkowski.WeLoveBooks.model.User;
import com.mateuszroszkowski.WeLoveBooks.repository.RoleRepository;
import com.mateuszroszkowski.WeLoveBooks.repository.UserRepository;
import com.mateuszroszkowski.WeLoveBooks.security.JWTProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                       AuthenticationManager authenticationManager, JWTProvider jwtProvider, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO userDTO){
        User user = new User();
        if(checkUsername(userDTO.getUsername())){
            user.setUsername(userDTO.getUsername());
        }
        else {
            throw new RuntimeException("That username is taken");
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        userDTO.getRoles().forEach(r -> roles
                .add(roleRepository.findByRoleName(r)
                        .orElseThrow(() -> new RuntimeException("Role doesn't exists"))));

        user.setRoles(roles);
        return userMapper.map(userRepository.save(user));

    }

    private boolean checkUsername(String username){
        return userRepository.findAll().stream().noneMatch(u -> u.getUsername().equals(username));
    }

    public String login(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        return token;
    }

    public UserDTO resetPassword(Long userId, ResetPasswordDTO passwordDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), passwordDTO.getOldPassword()));
        }
        catch (BadCredentialsException ex) {
            throw new RuntimeException("Bad credentials");
        }

        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        return userMapper.map(userRepository.save(user));
    }
}
