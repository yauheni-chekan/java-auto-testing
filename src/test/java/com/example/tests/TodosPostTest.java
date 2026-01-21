package com.example.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.domain.Todo;

import io.restassured.response.Response;

public class TodosPostTest extends BaseApiTest {
    private final Logger logger = LoggerFactory.getLogger(TodosPostTest.class);

    @Test
    void postTodos_validPayload_returnsCreatedTodo() {
        logger.info("Starting postTodos_validPayload_returnsCreatedTodo test");
        Todo request = new Todo(10, "test todo", false);

        Response response = todosClient.createTodo(request);
        logger.debug("Response: {}", response.body().asString());
        assertEquals(HttpStatus.SC_CREATED, response.statusCode());

        Todo created = response.as(Todo.class);
        assertEquals(request.getUserId(), created.getUserId());
        assertEquals(request.getTitle(), created.getTitle());
        assertEquals(request.getCompleted(), created.getCompleted());
        assertNotNull(created.getId());
        logger.info("postTodos_validPayload_returnsCreatedTodo test completed successfully");
    }

    @Test
    void postTodos_malformedJson_returnsServerError() {
        logger.info("Starting postTodos_malformedJson_returnsServerError test");
        Response response = todosClient.createTodoRaw("{");
        logger.debug("Response: {}", response.body().asString());
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.statusCode());
        assertFalse(response.body().asString().trim().isEmpty());
        logger.info("postTodos_malformedJson_returnsServerError test completed successfully");
    }
}
