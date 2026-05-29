package com.vp.jobportal.service;

import com.vp.job.dto.response.UserResponse;
import com.vp.jobportal.model.User;
import com.vp.jobportal.payload.UpdateUserRequest;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);

    List<User> getAllUsers();

    UserResponse updateUser(String email, UpdateUserRequest req );

    //admin actions
    UserResponse suspendUser(Long id) throws Exception;
    UserResponse activateUser(Long id) throws Exception;
    UserResponse deleteUser(Long id) throws Exception;
}
