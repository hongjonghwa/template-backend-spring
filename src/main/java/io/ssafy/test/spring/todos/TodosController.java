package io.ssafy.test.spring.todos;

import io.ssafy.test.spring.todos.dto.CreateTodoDto;
import io.ssafy.test.spring.todos.dto.UpdateTodoDto;
import io.ssafy.test.spring.todos.entities.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/todos")
@RequiredArgsConstructor
public class TodosController {

    final TodosService todosService;

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
