package io.ssafy.test.spring.auth;

import io.ssafy.test.spring.auth.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthUtils {
    public User getUserOrException() throws ResponseStatusException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // anonymous 처리
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You Need a Authorization");

        if (authentication.getPrincipal() instanceof CustomUserDetails){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = customUserDetails.getUser();
            if (user != null) return user;
        }

        // 기타 에러 처리
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Happened");
    }
}
