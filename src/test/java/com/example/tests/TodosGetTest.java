package com.example.tests;

import com.example.domain.Todo;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodosGetTest extends BaseApiTest {

    @Test
    void getTodos_returnsNonEmptyListWithRequiredFields() {
        Response response = todosClient.getTodos();

        assertEquals(200, response.statusCode());

        List<Todo> todos = response.jsonPath().getList("", Todo.class);
        assertNotNull(todos);
        assertFalse(todos.isEmpty());

        Todo first = todos.get(0);
        assertNotNull(first.getId());
        assertNotNull(first.getUserId());
        assertNotNull(first.getTitle());
        assertNotNull(first.getCompleted());
    }

    @Test
    void getTodoById_returnsExpectedData() {
        Response response = todosClient.getTodoById(1);

        assertEquals(200, response.statusCode());

        Todo todo = response.as(Todo.class);
        assertEquals(1, todo.getId());
        assertEquals(1, todo.getUserId());
        assertNotNull(todo.getTitle());
        assertNotNull(todo.getCompleted());
    }

    @Test
    void getTodoById_notFound_returns404AndEmptyObject() {
        Response response = todosClient.getTodoById(0);

        assertEquals(404, response.statusCode());
        assertEquals("{}", response.body().asString().trim());
    }
}
