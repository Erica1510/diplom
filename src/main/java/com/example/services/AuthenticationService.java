package com.example.services;

import com.example.controllers.AuthenticationRequest;
import com.example.dto.AuthenticatedUserDetails;

public interface AuthenticationService {
    AuthenticatedUserDetails authenticate(AuthenticationRequest request);
}
