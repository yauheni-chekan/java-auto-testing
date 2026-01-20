# Java Auto Testing Framework

A Test Automation Framework (TAF) for API testing using REST Assured, JUnit 5, and Maven. This framework provides a structured approach to testing REST APIs with a layered architecture pattern.

## TAF Architecture

The framework follows a layered architecture pattern that separates concerns and promotes maintainability:

### Layer Structure

```
src/
├── main/java/com/example/
│   ├── domain/          # Domain Models Layer
│   │   └── Todo.java    # Entity classes representing API data models
│   │
│   └── core/            # Core Framework Layer
│       ├── ApiConfig.java           # Centralized API configuration
│       └── client/                  # API Client Layer
│           └── TodosClient.java     # API client wrappers for endpoints
│
└── test/java/com/example/tests/     # Test Layer
    ├── BaseApiTest.java             # Base test class with common setup
    ├── TodosGetTest.java            # GET endpoint tests
    └── TodosPostTest.java           # POST endpoint tests
```

### Architecture Components

#### 1. Domain Layer (`com.example.domain`)
- **Purpose**: Contains domain models/entities that represent API request/response objects
- **Components**:
  - `Todo.java`: Domain model representing a Todo item with fields (id, userId, title, completed)
  - Uses Jackson annotations for JSON serialization/deserialization

#### 2. Core Layer (`com.example.core`)

**ApiConfig** (`com.example.core.ApiConfig`)
- Centralized configuration for API requests
- Defines base URI, content type, and logging filters
- Provides reusable `RequestSpecification` for all API calls
- Configures request/response logging (URI, METHOD, PARAMS, STATUS)

**Client Layer** (`com.example.core.client`)
- **TodosClient**: API client wrapper that encapsulates HTTP operations
- Provides methods for:
  - `getTodos()`: Retrieve all todos
  - `getTodoById(int id)`: Retrieve a specific todo by ID
  - `createTodo(Todo todo)`: Create a new todo with valid payload
  - `createTodoRaw(String rawJson)`: Create a todo with raw JSON string
- Uses SLF4J for logging API operations
- Abstracts REST Assured implementation details from test layer

#### 3. Test Layer (`com.example.tests`)

**BaseApiTest** (`com.example.tests.BaseApiTest`)
- Abstract base class for all API tests
- Provides common setup via `@BeforeEach`:
  - Initializes `TodosClient` instance for test classes
- Promotes code reuse and consistent test initialization

**Test Classes**
- Extend `BaseApiTest` to inherit common setup
- Use JUnit 5 (`@Test`) for test execution
- Use SLF4J for test logging
- Assertions using JUnit 5 assertions

## Test Descriptions

### TodosGetTest

Tests for GET endpoints of the Todos API.

#### `getTodos_returnsNonEmptyListWithRequiredFields()`
- **Purpose**: Validates successful retrieval of all todos
- **Verifications**:
  - HTTP status code is 200
  - Response contains a non-empty list
  - Each todo item has required fields: id, userId, title, completed

#### `getTodoById_returnsExpectedData()`
- **Purpose**: Validates retrieval of a specific todo by ID
- **Test Data**: ID = 1
- **Verifications**:
  - HTTP status code is 200
  - Response matches expected todo with id=1, userId=1
  - Title and completed fields are not null

#### `getTodoById_notFound_returns404AndEmptyObject()`
- **Purpose**: Validates error handling for non-existent todo
- **Test Data**: ID = 0 (non-existent)
- **Verifications**:
  - HTTP status code is 404
  - Response body is empty JSON object `{}`

### TodosPostTest

Tests for POST endpoints of the Todos API.

#### `postTodos_validPayload_returnsCreatedTodo()`
- **Purpose**: Validates successful creation of a new todo
- **Test Data**: Todo with userId=10, title="test todo", completed=false
- **Verifications**:
  - HTTP status code is 201 (Created)
  - Created todo matches request data (userId, title, completed)
  - Server assigns a new ID to the created todo

#### `postTodos_malformedJson_returnsServerError()`
- **Purpose**: Validates error handling for invalid JSON payload
- **Test Data**: Malformed JSON string `"{`
- **Verifications**:
  - HTTP status code is 500 (Server Error)
  - Response body contains error information (not empty)

## Technologies

- **Java 25**: Programming language
- **Maven**: Build tool and dependency management
- **REST Assured 6.0.0**: API testing library
- **JUnit Jupiter 6.0.1**: Testing framework
- **Jackson 2.19.4**: JSON serialization/deserialization
- **SLF4J 2.0.17**: Logging facade
- **Logback 1.5.24**: Logging implementation (test scope)

## Running Tests

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=TodosGetTest
mvn test -Dtest=TodosPostTest
```

### Run specific test method
```bash
mvn test -Dtest=TodosGetTest#getTodos_returnsNonEmptyListWithRequiredFields
```

## Logging

Test execution logs are configured via `logback-test.xml` in `src/test/resources/`:
- DEBUG level for `com.example` package
- INFO level for root logger
- Console output with timestamps, thread names, and logger names
- Request/response logging via REST Assured filters

## API Under Test

The framework tests the JSONPlaceholder Todos API:
- **Base URI**: `https://jsonplaceholder.typicode.com`
- **Endpoints**:
  - `GET /todos` - Get all todos
  - `GET /todos/{id}` - Get todo by ID
  - `POST /todos` - Create a new todo
