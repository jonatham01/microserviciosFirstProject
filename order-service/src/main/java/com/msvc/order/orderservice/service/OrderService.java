package com.msvc.order.orderservice.service;

//import brave.Span;
//import brave.Tracer;
import com.msvc.order.orderservice.config.WebClientConfig;
import com.msvc.order.orderservice.dto.InventarioResponse;
import com.msvc.order.orderservice.dto.OrderLineItemsDto;
import com.msvc.order.orderservice.dto.OrderRequest;
import com.msvc.order.orderservice.event.OrderPlacedEvent;
import com.msvc.order.orderservice.model.Order;
import com.msvc.order.orderservice.model.OrderLineItems;
import com.msvc.order.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@Slf4j
@Service
public class OrderService {

    @Autowired(required = true)
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webCliBuilder;

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

        InventarioResponse[] inventarioResponseArray = webCliBuilder.build().get()
                .uri("http://inventario-service/api/inventario",uriBuilder -> uriBuilder.queryParam("codigoSku",codigoSku).build())
                // .header("Authorization", "Bearer MY_SECRET_TOKEN")
                .retrieve()
                .bodyToMono(InventarioResponse[].class)
                .block();

        assert inventarioResponseArray != null;
        boolean allProductosInStock = Arrays.stream(inventarioResponseArray)
                .allMatch(InventarioResponse::isInStock);

        if(allProductosInStock){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getNumeroPedido()));

            return "orden realizada";
        }
        else{

            return"ok";

        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setCantidad(orderLineItemsDto.getCantidad());
        orderLineItems.setPrecio(orderLineItemsDto.getPrecio());
        orderLineItems.setCodigoSku(orderLineItemsDto.getCodigoSku());
        return orderLineItems;
    }
}

