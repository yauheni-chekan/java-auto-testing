package com.example.tests;

import org.junit.jupiter.api.BeforeEach;

import com.example.core.client.TodosClient;

public abstract class BaseApiTest {
    protected TodosClient todosClient;

    @BeforeEach
    void setUp() {
        todosClient = new TodosClient();
    }
}
