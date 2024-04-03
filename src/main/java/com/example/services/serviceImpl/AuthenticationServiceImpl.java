package com.example.services.serviceImpl;

import com.example.controllers.AuthenticationRequest;
import com.example.dto.AuthenticatedUserDetails;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.security.JwtService;
import com.example.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticatedUserDetails authenticate(AuthenticationRequest request) {
        String requestUsername = request.getUsername();
        String requestPassword = request.getPassword();

        User user = repository.findByUsername(requestUsername)
                .orElseThrow();

        authenticate(user, requestPassword);

        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);
        return buildAuthenticatedUserDetails(user, accessToken, refreshToken);
    }

    private void authenticate(User user, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), password));
    }

    private AuthenticatedUserDetails buildAuthenticatedUserDetails(User user, String accessToken, String refreshToken) {
        return AuthenticatedUserDetails.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }
}
