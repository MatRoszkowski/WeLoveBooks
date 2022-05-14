package com.mateuszroszkowski.WeLoveBooks.security;

import com.mateuszroszkowski.WeLoveBooks.model.User;
import com.mateuszroszkowski.WeLoveBooks.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository.findByUsername(s).orElseThrow(() -> new RuntimeException("User not found"));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserPrincipal.create(user);
    }
}
