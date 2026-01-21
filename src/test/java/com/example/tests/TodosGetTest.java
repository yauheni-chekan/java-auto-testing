package com.example.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.domain.Todo;

import io.restassured.response.Response;

public class TodosGetTest extends BaseApiTest {
    private final Logger logger = LoggerFactory.getLogger(TodosGetTest.class);

    @Test
    void getTodos_returnsNonEmptyListWithRequiredFields() {
        logger.info("Starting getTodos_returnsNonEmptyListWithRequiredFields test");
        Response response = todosClient.getTodos();

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        List<Todo> todos = response.jsonPath().getList("", Todo.class);
        assertNotNull(todos);
        assertFalse(todos.isEmpty());

        Todo first = todos.get(0);
        assertNotNull(first.getId());
        assertNotNull(first.getUserId());
        assertNotNull(first.getTitle());
        assertNotNull(first.getCompleted());
        logger.info("getTodos_returnsNonEmptyListWithRequiredFields test completed successfully");
    }

    @Test
    void getTodoById_returnsExpectedData() {
        logger.info("Starting getTodoById_returnsExpectedData test");
        Response response = todosClient.getTodoById(1);
        logger.debug("Response: {}", response.body().asString());
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        Todo todo = response.as(Todo.class);
        assertEquals(1, todo.getId());
        assertEquals(1, todo.getUserId());
        assertNotNull(todo.getTitle());
        assertNotNull(todo.getCompleted());
        logger.info("getTodoById_returnsExpectedData test completed successfully");
    }

    @Test
    void getTodoById_notFound_returns404AndEmptyObject() {
        logger.info("Starting getTodoById_notFound_returns404AndEmptyObject test");
        Response response = todosClient.getTodoById(0);
        logger.debug("Response: {}", response.body().asString());
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
        assertEquals("{}", response.body().asString().trim());
        logger.info("getTodoById_notFound_returns404AndEmptyObject test completed successfully");
    }
}
