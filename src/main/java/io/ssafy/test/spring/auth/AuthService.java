package io.ssafy.test.spring.auth;

import io.ssafy.test.spring.auth.dto.LoginRequest;
import io.ssafy.test.spring.auth.dto.CreateUserDto;
import io.ssafy.test.spring.auth.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService {

    final PasswordEncoder passwordEncoder;
    final JwtUtils jwtUtils;
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;

    public boolean isAvailableUsername(String username) {
        return !userRepository.existsByUsername(username);
    }

    public User registerUser(CreateUserDto createUserDto) {

        String username = createUserDto.getUsername().trim().toLowerCase();
        String password = createUserDto.getPassword().trim();
        String role = "USER";
        String name = createUserDto.getName();

        if (!isAvailableUsername(username))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User Already Existed");

        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .role(role)
                .build();
        userRepository.save(newUser);

        return newUser;
    }

    public String getAccessToken(LoginRequest req) {
        String username = req.getUsername().trim().toLowerCase();
        String password = req.getPassword().trim();

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch(BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Username or Password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        return jwtUtils.generateJwt(customUserDetails.getUsername());
    }

}
