package com.demo.route;

import com.demo.model.Customer;
import com.demo.service.impl.CustomerServiceImpl;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // For more info of config see: http://camel.apache.org/swagger-java.html
        // @formatter:off
        restConfiguration()
                .component("netty4-http")
                    .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/")
                    .port(8090) // Should not be same tomcat port.
                .apiContextPath("/api-doc")
                    .apiProperty("api.title", "Customer REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true");

        // This sample consume/produce json only.
        // To access rest endpoint it must use the port in restConfiguration instead of the tomcat/server port.
        // ex: http://localhost:8090/customers
        rest("/customers")
                .description("Customer service REST API")
                .consumes(APPLICATION_JSON_VALUE)
                .produces(APPLICATION_JSON_VALUE)
                    .get("/")
                        .description("Find all list of customers")
                        .route().routeId("find-all-customers")
                        .bean(CustomerServiceImpl.class, "getAllCustomers")
                        .log("No. of customers ${body}")
                        .endRest().outType(Customer[].class)
                    .post("/")
                        .description("Add new customer")
                        .param().name("body").type(RestParamType.body).description("Customer info to be added").endParam().type(Customer.class)
                        .route().routeId("add-customer")
                        .bean(CustomerServiceImpl.class, "addCustomer(${body})")
                        .log("New customer added [${body.id} ${body.firstname} ${body.lastname}]")
                        .endRest().outType(Customer.class)
                    .get("/{id}")
                        .description("Find customer by id")
                        .param().name("id").type(RestParamType.path).description("Customer id").endParam()
                        .route().routeId("find-customer-by-id")
                        .bean(CustomerServiceImpl.class, "getCustomerById(${header.id})")
                        .log("Find customer ${header.id} [${body.firstname} ${body.lastname}]")
                        .endRest().outType(Customer.class)
                    .delete("/{id}")
                        .description("Delete customer by id")
                        .param().name("id").type(RestParamType.path).description("Customer id").endParam()
                        .route().routeId("delete-customer-by-id")
                        .bean(CustomerServiceImpl.class, "removeCustomerById(${header.id})")
                        .log("Remove customer ${header.id}")
                        .endRest().responseMessage().code(HttpStatus.GONE.value()).endResponseMessage()
                    .put("/{id}")
                        .description("Update customer by id")
                        .param().name("id").type(RestParamType.path).description("Customer id").endParam()
                        .param().name("body").type(RestParamType.body).description("Customer info to be updated").endParam().type(Customer.class)
                        .route().routeId("update-customer-by-id")
                        .bean(CustomerServiceImpl.class, "updateCustomerById(${header.id}, ${body})")
                        .log("Update customer ${header.id}")
                        .endRest();
        // @formatter:on
    }
}