package com.example.tests;

import com.example.core.client.TodosClient;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiTest {
    protected TodosClient todosClient;

    @BeforeEach
    void setUp() {
        todosClient = new TodosClient();
    }
}
