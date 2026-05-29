package com.vp.jobportal.security;

import com.vp.jobportal.model.User;
import com.vp.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch user by username
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found" + username);
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        Collection<GrantedAuthority> authorities = Collections.singleton(authority);

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
    }
}
