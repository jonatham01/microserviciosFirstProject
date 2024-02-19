package com.msvc.order.orderservice.service;

//import brave.Span;
//import brave.Tracer;
import com.msvc.order.orderservice.config.WebClientConfig;
import com.msvc.order.orderservice.dto.InventarioResponse;
import com.msvc.order.orderservice.dto.OrderLineItemsDto;
import com.msvc.order.orderservice.dto.OrderRequest;
import com.msvc.order.orderservice.model.Order;
import com.msvc.order.orderservice.model.OrderLineItems;
import com.msvc.order.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@Slf4j
@Service
public class OrderService { 

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClient;

   /// @Autowired(required = true)
    //private Tracer tracer;

    //@Transactional
    //@SneakyThrows //oculta la excepcion al compilador, pero en ejecucion si se arroja
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setNumeroPedido(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        List<String> codigoSku =order.getOrderLineItems().stream()
                .map(OrderLineItems::getCodigoSku)
                .toList();

        InventarioResponse[] inventarioResponseArray = webClient.build().get()
                //.uri("http://inventario-service/api/inventario",
                .uri("http://localhost:8083/api/inventario",
                        uriBuilder -> uriBuilder.queryParam("codigoSku",codigoSku).build()
                ).retrieve()
                .bodyToMono(InventarioResponse[].class)
                .block();

        assert inventarioResponseArray != null;
        boolean allProductosInStock = Arrays.stream(inventarioResponseArray)
                .allMatch(InventarioResponse::isInStock);

        if(allProductosInStock){
            orderRepository.save(order);
            return "orden realizada";
        }
        else{
            throw new IllegalArgumentException("El producto no esta en stock");
        }
    }
    /*@Transactional
    @SneakyThrows //oculta la excepcion al compilador, pero en ejecucion si se arroja
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setNumeroPedido(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        List<String> codigoSku =order.getOrderLineItems().stream()
                        .map(OrderLineItems::getCodigoSku)
                                .toList();

        //trace de un service
        Span inventarioServiceLookup = tracer.nextSpan().name("InventarioServiceLookup");

        try(Tracer.SpanInScope isLookUp =
                    tracer.withSpanInScope(inventarioServiceLookup.start())
        ){
            inventarioServiceLookup.tag("call","inventario-service");

            InventarioResponse[] inventarioResponseArray = webClient.build().get()
                    .uri("http://inventario-service/api/inventario",
                            //.uri("http://localhost:8082/api/inventario",
                            uriBuilder -> uriBuilder.queryParam("codigoSku",codigoSku).build()
                    ).retrieve()
                    .bodyToMono(InventarioResponse[].class)
                    .block();

            assert inventarioResponseArray != null;
            boolean allProductosInStock = Arrays.stream(inventarioResponseArray)
                    .allMatch(InventarioResponse::isInStock);

            if(allProductosInStock){
                orderRepository.save(order);
                return "pedido exitoso";
            }
            else{
                throw new IllegalArgumentException("El producto no esta en stock");
            }

        }finally {
            inventarioServiceLookup.flush();
        }


    }*/

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setCantidad(orderLineItemsDto.getCantidad());
        orderLineItems.setPrecio(orderLineItemsDto.getPrecio());
        orderLineItems.setCodigoSku(orderLineItemsDto.getCodigoSku());
        return orderLineItems;
    }
}

/*
public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setNumeroPedido(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        List<String> codigoSku =order.getOrderLineItems().stream()
                        .map(OrderLineItems::getCodigoSku)
                                .toList();

        InventarioResponse[] inventarioResponseArray = webClient.build().get()
                        .uri("http://inventario-service/api/inventario",
                        //.uri("http://localhost:8082/api/inventario",
                                uriBuilder -> uriBuilder.queryParam("codigoSku",codigoSku).build()
                        ).retrieve()
                        .bodyToMono(InventarioResponse[].class)
                        .block();

        boolean allProductosInStock = Arrays.stream(inventarioResponseArray)
                        .allMatch(InventarioResponse::isInStock);

        if(allProductosInStock){
            orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("El producto no esta en stock");
        }
    }

 */