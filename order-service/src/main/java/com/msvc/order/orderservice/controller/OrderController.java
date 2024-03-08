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
