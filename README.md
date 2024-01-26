## Store Management Tool
API that acts as a store management tool.

Source code: `https://github.com/ccoman25/store-management`

### Futures

* #### API endpoints:
  * Get products from store `[GET] http://host:port/api/product/findAll`
  * Find by category `[GET] http://host:port/api/product/category/{category_name}`
  * Find by id `[GET] http://host:port/api/product/id/{id}`
  * Add product `[POST] http://host:port/api/product/insert`
  * Update product `[PUT] http://host:port/api/product/{id}`
  * Delete product `[DELETE] http://host:port/api/product/{id}`
  
* #### Error handling mechanism
 The main objective of this mechanism is to provide a means to detects and reports an error so that the program can either handle it and continue or terminate gracefully.
In this API the following errors were handled:

1. MethodArgumentNotValidException
2. HttpMessageNotReadableException
3. ProductNotFoundException
4. ProductMalformatException

`GlobalExceptionHandler` handles those exceptions. It exposes a clear message to the client when something unexpected occurred.

Example of request with validation error that was handled.

```
[PUT] http://host:port/api/product/1

Request body:

{   "id": 1,
    "name": "OnePlus",
    "category": "phone2",
    "price": 11,
    "description": "A smart phone"
}

Response:
{
    "statusCode": 422,
    "message": "Validation failed",
    "errors": [
        {
            "field": "category",
            "message": "Category name should contain only letters"
        },
        {
            "field": "price",
            "message": "must be greater than or equal to 100"
        }
    ]
}
```
```
[GET] http://host:port/api/product/id/30
Response body:
{
    "statusCode": 404,
    "message": "Validation failed",
    "errors": [
        {
            "field": "id",
            "message": "Product [id=30] was not found"
        }
    ]
}
```

* #### Logging
Logging is done with SLF4j and Logback.
The logs are redirected to console and a file if the application is running outside of a container,
otherwise the logs will be redirected to the console (`logback.xml`)

* #### Basic authentication mechanism and role based endpoint access
1. Libraries: spring-boot-starter-security 3.2.2 
2. Generate token:

    Endpoint: `[GET ]http://host:port/auth/login`

    Request body:
```
{
    "username": "admin",
    "password": "admin"
}

OR

{
    "username": "user",
    "password": "password"
}
```
Response body: A token

  ```Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlVTRVIiLCJleHAiOjE3MDYxOTUwMTYsImlhdCI6MTcwNjE5MTQxNn0.FTlI_cDbKXs07DPWsZvP41C34PxakLCsnFJtih0zcFfolhMmuv-VhYRhmgAYhhwJWA1xj-8qc93Tl1Swc7UzXw```

`http://jwt.io` - decode token and visualize the payload.

  The token will be used as an `Authorization` header for all requests.

  * #### Unit test
`ProductService` contains unit test methods using Mockito.
  
* #### Run and deploy the Store Management Tool Application
Docker is used for creating containers for MongoDB and Store Management Application.
Steps to run the app:
1. install docker compose
2. Create Dockerfile - jar app info
3. Create docker-compose.yml
   * mongo-db container
   * storemanagementtool container
4. Pull the latest mongo image: `docker pull mongo`
5. Build application: `mvn clean install`
6. Create Store Mng Tool image: `docker build -t storemanagementtool .`
7. Run docker compose: `docker compose up`

The endpoints can be accessed inside container using port `9090` or outside the container using port `8090`
```
spring.data.mongodb.host: localhost  -outside
spring.data.mongodb.host: mongo-db  - inside
```
 `Mongo DB Compass` - view database table: `product`
  
Connection: `mongodb://{host}:27017`
