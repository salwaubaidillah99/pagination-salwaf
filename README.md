# Paginated Users API (Spring Boot)
This project is a Spring Boot REST API that consumes an external API and provides pagination, filtering, caching, and proper error handling.
The external API does not support pagination, so pagination is implemented internally.

---

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Web
- Spring Cache
- Lombok
- Springdoc OpenAPI (Swagger)

---

## How to Run the Project

### Prerequisites
- Java 17+
- Maven 3.8+

### Steps
1. Clone the repository
   ```bash
   git clone https://github.com/salwaubaidillah99/pagination-salwaf
   cd pagination

2. Build and run the application 
   ```bash
   mvn clean spring-boot:run
   ```
3. The application will start on: 
    ```http://localhost:8080 ```

4. Swagger UI:
   ```http://localhost:8080/swagger-ui.html```

5. API Example :
   ```
   GET /api/users?page=1&size=10
   GET /api/users?name=John&page=1&size=5
    ```
   Example response:
   ````
   {
   "page": 1,
   "size": 10,
   "totalItems": 208,
   "totalPages": 21,
   "data": []
   }
   ````
   Error Handling 
   ```
   400 → Invalid page or size
   500 → External API error
   ```

   Assumptions 
   ```
   External API does not support pagination
   Data is fetched once and cached
   Pagination and filtering are handled internally
   ```
   External API:
   ```https://dummyjson.com/users```

   

