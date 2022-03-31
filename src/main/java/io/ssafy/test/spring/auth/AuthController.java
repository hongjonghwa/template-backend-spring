package io.ssafy.test.spring.auth;

import io.ssafy.test.spring.auth.dto.LoginRequest;
import io.ssafy.test.spring.auth.dto.CreateUserDto;
import io.ssafy.test.spring.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(value = "api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    final AuthService authService;

    @PostMapping("register")
    User register(@Valid @RequestBody CreateUserDto dto){
        return authService.registerUser(dto);
    }

    @GetMapping("lookup/{username}")
    Boolean lookupUsername(@NotBlank @Size(min=4) @PathVariable String username) {
        return authService.isAvailableUsername(username);
    }

    @PostMapping("login")
    String login(@Valid @RequestBody LoginRequest req){
        return authService.getAccessToken(req);
    }
}
