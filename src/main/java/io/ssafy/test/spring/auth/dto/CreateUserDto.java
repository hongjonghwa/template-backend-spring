package io.ssafy.test.spring.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateUserDto {

    @Size(min=4,message = "Username cannot be shorter than 4 chars")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Name cannot be empty")
    private String name;

}
