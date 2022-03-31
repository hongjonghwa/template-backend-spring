package io.ssafy.test.spring.todos.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTodoDto {
    @NotBlank
    private String title;
    @NotNull
    private String contents;
}
