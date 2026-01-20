package com.example.tests;

import com.example.domain.Todo;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodosGetTest extends BaseApiTest {
    private final Logger logger = LoggerFactory.getLogger(TodosGetTest.class);

    @Test
    void getTodos_returnsNonEmptyListWithRequiredFields() {
        logger.info("Starting getTodos_returnsNonEmptyListWithRequiredFields test");
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
        logger.info("getTodos_returnsNonEmptyListWithRequiredFields test completed successfully");
    }

    @Test
    void getTodoById_returnsExpectedData() {
        logger.info("Starting getTodoById_returnsExpectedData test");
        Response response = todosClient.getTodoById(1);
        logger.debug("Response: {}", response.body().asString());
        assertEquals(200, response.statusCode());

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
        assertEquals(404, response.statusCode());
        assertEquals("{}", response.body().asString().trim());
        logger.info("getTodoById_notFound_returns404AndEmptyObject test completed successfully");
    }
}
