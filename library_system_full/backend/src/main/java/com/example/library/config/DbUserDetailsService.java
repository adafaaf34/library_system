package com.example.library.config;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DbUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByPhone(username);
        if (u == null) throw new UsernameNotFoundException("user not found");
        // stored password is bcrypt of (rawPassword + salt)
        return new org.springframework.security.core.userdetails.User(u.getPhoneNumber(), u.getPasswordHash(), Collections.emptyList());
    }
}
