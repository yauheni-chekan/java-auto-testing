package com.example.core.client;

import com.example.core.ApiConfig;
import com.example.domain.Todo;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class TodosClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodosClient.class);

    public Response getTodos() {
        LOGGER.info("GET /todos");
        return given()
                .spec(ApiConfig.requestSpec())
                .when()
                .get("/todos");
    }

    public Response getTodoById(int id) {
        LOGGER.info("GET /todos/{}", id);
        return given()
                .spec(ApiConfig.requestSpec())
                .pathParam("id", id)
                .when()
                .get("/todos/{id}");
    }

    public Response createTodo(Todo todo) {
        LOGGER.info("POST /todos (valid payload)");
        return given()
                .spec(ApiConfig.requestSpec())
                .body(todo)
                .when()
                .post("/todos");
    }

    public Response createTodoRaw(String rawJson) {
        LOGGER.info("POST /todos (raw payload)");
        return given()
                .spec(ApiConfig.requestSpec())
                .body(rawJson)
                .when()
                .post("/todos");
    }
}
