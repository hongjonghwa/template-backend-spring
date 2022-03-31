package io.ssafy.test.spring.todos;

import io.ssafy.test.spring.auth.AuthUtils;
import io.ssafy.test.spring.todos.dto.CreateTodoDto;
import io.ssafy.test.spring.todos.dto.UpdateTodoDto;
import io.ssafy.test.spring.todos.entities.Todo;
import io.ssafy.test.spring.util.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodosService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TodosRepository todosRepository;

    @Autowired
    AuthUtils authUtils;

    List<Todo> findAll(){
        return todosRepository.findAll();
    };

    Todo findById(long id){
        return todosRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Not Found")
        );
    };

    Todo create(CreateTodoDto createTodoDto){
        Todo newTodo = objectMapper.map(createTodoDto, Todo.class);
        newTodo.setOwner(authUtils.getUserOrException());
        return todosRepository.save(newTodo);
    }

    Todo updateById(Long id, UpdateTodoDto updateTodoDto){
        Todo todo = todosRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found")
        );
        if (!todo.getOwner().equals(authUtils.getUserOrException()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are Not Owner");

        objectMapper.map(updateTodoDto, todo);

        return todosRepository.save(todo);
    }

    void deleteById(Long id){
        Todo todo = todosRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found")
        );
        if (!todo.getOwner().equals(authUtils.getUserOrException()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are Not Owner");

        todosRepository.deleteById(id);
    }

}
