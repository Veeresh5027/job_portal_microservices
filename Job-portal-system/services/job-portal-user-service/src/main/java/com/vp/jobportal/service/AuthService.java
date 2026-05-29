package com.vp.jobportal.service;

import com.vp.jobportal.payload.AuthResponse;
import com.vp.jobportal.payload.LoginRequest;
import com.vp.jobportal.payload.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest req);

    AuthResponse login(LoginRequest req);
}
