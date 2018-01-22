# Getting Started
This is a sample implementation of Apache camel Rest DSL http://camel.apache.org/rest-dsl.html on spring boot project using jpa. 
<br/>
It uses Swagger for API documentation.

## Configuration
Here is the sample rest configuration using the swagger and netty4-http component.
<br/>
See: http://camel.apache.org/swagger-java.html
<br/>
```
 restConfiguration()
       .component("netty4-http")
           .bindingMode(RestBindingMode.json)
       .dataFormatProperty("prettyPrint", "true")
       .contextPath("/api")
           .port(8090)
       .apiContextPath("/api-doc")
           .apiProperty("api.title", "Customer REST API")
           .apiProperty("api.version", "1.0")
           .apiProperty("cors", "true");
```

## How it Works
* http://localhost:8090/api/customers
* http://localhost:8090/api/customers/{customerId}

This is the customer endpoint which perform CRUD operation using the in-memory H2 database. The port used must be same with the one set on the restConfiguration and also the contextPath(which is set to "/api").
<br/>
<br/>
Logging level of camel is set to debug, you can change it to avoid verbosity of logs.

* org.apache.camel: debug

## Swagger
You can access the api documentation in http://localhost:8090/api/api-doc. See the Configuration part for the context path.
