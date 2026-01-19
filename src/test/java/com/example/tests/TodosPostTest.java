package com.example.tests;

import com.example.domain.Todo;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodosPostTest extends BaseApiTest {

    @Test
    void postTodos_validPayload_returnsCreatedTodo() {
        Todo request = new Todo(10, "test todo", false);

        Response response = todosClient.createTodo(request);

        assertEquals(201, response.statusCode());

        Todo created = response.as(Todo.class);
        assertEquals(request.getUserId(), created.getUserId());
        assertEquals(request.getTitle(), created.getTitle());
        assertEquals(request.getCompleted(), created.getCompleted());
        assertNotNull(created.getId());
    }

    @Test
    void postTodos_malformedJson_returnsServerError() {
        Response response = todosClient.createTodoRaw("{");

        assertEquals(500, response.statusCode());
        assertFalse(response.body().asString().trim().isEmpty());
    }
}
