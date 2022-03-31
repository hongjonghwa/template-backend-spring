package io.ssafy.test.spring.todos;

import io.ssafy.test.spring.auth.CustomUserDetails;
import io.ssafy.test.spring.auth.entities.User;
import io.ssafy.test.spring.todos.dto.CreateTodoDto;
import io.ssafy.test.spring.todos.dto.UpdateTodoDto;
import io.ssafy.test.spring.todos.entities.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/todos")
public class TodosController {

    @Autowired
    TodosService todosService;

    private final Logger logger = LoggerFactory.getLogger(TodosController.class);

    /*
    void debug(){
        logger.info("Debug :");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("authentication - " + authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("customUserDetails - " + customUserDetails);
        User user = customUserDetails.getUser();
        logger.info("user - " + user);

    }
     */

    @GetMapping
    List<Todo> findAll() {
        //debug();
        return todosService.findAll();
    }

    @PostMapping
    Todo create(@Valid @RequestBody CreateTodoDto dto) { return todosService.create(dto); }

    @GetMapping("{id}")
    Todo read(@PathVariable Long id) { return todosService.findById(id); }

    @PutMapping("{id}")
    Todo update(@Valid @RequestBody UpdateTodoDto dto, @PathVariable Long id) { return todosService.updateById(id, dto); }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) { todosService.deleteById(id); }

}
