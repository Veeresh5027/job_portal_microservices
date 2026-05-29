package com.vp.jobportal.service.impl;

import com.vp.job.domain.UserRole;
import com.vp.job.domain.UserStatus;
import com.vp.jobportal.mapper.UserMapper;
import com.vp.jobportal.model.User;
import com.vp.jobportal.payload.AuthResponse;
import com.vp.jobportal.payload.LoginRequest;
import com.vp.jobportal.payload.SignupRequest;
import com.vp.jobportal.repository.UserRepository;
import com.vp.jobportal.security.CustomUserDetailsService;
import com.vp.jobportal.security.JwtProvider;
import com.vp.jobportal.service.AuthService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponse signup(SignupRequest req) {
        // 1. Validate if user exists
        if(userRepository.findByEmail(req.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }
        if(req.getRole() == UserRole.ROLE_ADMIN){
            throw new RuntimeException("Admin cannot be created by self-registration");
        }

        // 2. Build the User object
        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .phone(req.getPhone())
                .status(UserStatus.ACTIVE)
                .lastLogin(LocalDateTime.now())
                .build();

        // 3. Save the built object
        User savedUser = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        // 4. Build Response
        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome " + req.getFullName());
        res.setMessage("Registration successful");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(savedUser));
        return res;
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        Authentication authentication = authenticate(
                req.getEmail(),
                req.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(req.getEmail());
        String jwt = jwtProvider.generateToken(authentication, user.getId());
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome Back " + user.getFullName());
        res.setMessage("Login successful");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(user));
        return res;
    }

    private Authentication authenticate(String email, String password)  {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if(userDetails == null) {
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
        );
    }
}