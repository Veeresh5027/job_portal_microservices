package com.vp.jobportal.service.impl;

import com.vp.job.domain.UserStatus;
import com.vp.job.dto.response.UserResponse;
import com.vp.jobportal.mapper.UserMapper; // Kept to use statically
import com.vp.jobportal.model.User;
import com.vp.jobportal.payload.UpdateUserRequest;
import com.vp.jobportal.repository.UserRepository;
import com.vp.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse updateUser(String email, UpdateUserRequest req) {
        User user = getUserByEmail(email);
        if (req.getFullName() != null){
            user.setFullName(req.getFullName());
        }
        if (req.getPhone() != null){
            user.setPhone(req.getPhone());
        }
        if (req.getProfileImage() != null){
            user.setProfileImage(req.getProfileImage());
        }


        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse suspendUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());

        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);

        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());

        return UserMapper.toDTO(userRepository.save(user));
    }
}