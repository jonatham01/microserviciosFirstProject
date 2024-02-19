package com.msvc.order.orderservice.controller;

import com.msvc.order.orderservice.dto.OrderRequest;
import com.msvc.order.orderservice.service.OrderService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.retry.annotation.Retry;
//import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
/*
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name="inventario", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventario")
    @Retry(name = "inventario")
    public CompletableFuture<String> realizarPedido(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()-> "Oops ha ocurrido un error al realizar el pedido");
    }
}*/

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
public String realizarPedido(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "pedido realizado con exito";
}}


/*
  public CompletableFuture<String> realizarPedido(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest));

 */


/*zepkin



management.health.circuitbreakers.enabled=true
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
feign.circuitbreaker.enabled=true


resilience4j.circuitbreaker.instances.inventario.registerHealthIndicator=true
resilience4j.circuitbreaker.instances.inventario.event-consumer-buffer-size=10

#HACE UN CONTEO DEL RESULTADO DE LAS LLAMADAS
resilience4j.circuitbreaker.instances.inventario.slidingWindowType=COUNT_BASED

#Tamaño de la ventana deslizante
resilience4j.circuitbreaker.instances.inventario.slidingWindowSize=5

#porcentaje de falla. y luego se abre el circuito
resilience4j.circuitbreaker.instances.inventario.failureRateThreshold=50

#tiempo de duracion en estado abierto
resilience4j.circuitbreaker.instances.inventario.waitDurationInOpenState=5s

#numero de llamadas en half open
resilience4j.circuitbreaker.instances.inventario.permittedNumberOfCallsInHalfOpenState=3

#automatica transition de open a half
resilience4j.circuitbreaker.instances.inventario.automaticTransitionFromOpenToHalfOpenEnabled=true

#estara out por 3segundos
resilience4j.timelimiter.instances.inventario.timeout-duration=3s

#maximo de intentos
resilience.retry.instances.inventario.max-attempts=3

#duracion de espera
resilience.retry.instances.inventario.wait-duration=90s


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>


spring.zipkin.base-url=http://localhost:9411
#spring.sleuth.sampler.probability=1.0
spring.cloud.compatibility-verifier.enabled=false

        <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
			<version>3.1.5</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-sleuth-zipkin</artifactId>
			<version>3.1.9</version>
		</dependency>

		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-tracing-bridge-brave</artifactId>
		</dependency>

 */