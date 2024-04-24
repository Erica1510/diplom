package com.example.config;


import com.example.security.CorsFilter;
import com.example.security.FilterChainExceptionHandler;
import com.example.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private static final String USER_ENDPOINT = "api/v1/all-users";
    private static final String BY_ID_USER_ENDPOINT = "api/v1/users/{id}";

    private static final String REGISTRATION_ENDPOINT = "api/v1/register/**";
    private static final String AUTHENTICATION_ENDPOINT = "api/v1/auth/**";
    private static final String LOGOUT_ENDPOINT = "api/v1/logout/**";

    private static final String ROOM_ENDPOINT = "api/v1/room/**";
    private static final String FIND_BY_NAME_ROOM_ENDPOINT = "api/v1/room/{name}";
    private static final String ALL_ROOM_ENDPOINT = "api/v1/room/all";
    private static final String ADD_USER_TO_CHAT = "api/v1/room/add-users/{chatRoomId}";
    private static final String SEND_MESSAGE = "api/v1/room/send-message";


    private final JwtAuthFilter jwtAuthFilter;

    private final FilterChainExceptionHandler filterChainExceptionHandler;

    private final UserDetailsService userDetailsService;

    private final CorsFilter corsFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(BY_ID_USER_ENDPOINT,FIND_BY_NAME_ROOM_ENDPOINT,REGISTRATION_ENDPOINT,SEND_MESSAGE,ADD_USER_TO_CHAT,LOGOUT_ENDPOINT, ALL_ROOM_ENDPOINT, AUTHENTICATION_ENDPOINT, USER_ENDPOINT, ROOM_ENDPOINT).permitAll()
                                .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, JwtAuthFilter.class)
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
