package io.ssafy.test.spring.todos;

import io.ssafy.test.spring.todos.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodosRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTitle(String title);
}
