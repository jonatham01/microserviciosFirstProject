package com.msvc.order.orderservice.service;

import com.msvc.order.orderservice.dto.InventarioResponse;
import com.msvc.order.orderservice.dto.OrderLineItemsDto;
import com.msvc.order.orderservice.dto.OrderRequest;
import com.msvc.order.orderservice.model.Order;
import com.msvc.order.orderservice.model.OrderLineItems;
import com.msvc.order.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService2Prueba {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webclient;

    public String placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setNumeroPedido(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItems(orderLineItemsList);

        List<String> codigoSku = order.getOrderLineItems()
                .stream()
                .map(OrderLineItems::getCodigoSku)
                .toList();

        InventarioResponse[] inventarioResponseArray = webclient.build().get()
                .uri("http://inventario-service/api/inventario", uriBuilder -> uriBuilder.queryParam("codigoSku",codigoSku).build())
                .retrieve()
                .bodyToMono(InventarioResponse[].class)
                .block();

        assert inventarioResponseArray != null;
        boolean inventarioIsInStock = Arrays.stream(inventarioResponseArray)
                .allMatch(InventarioResponse::isInStock);

        if(inventarioIsInStock){
            orderRepository.save(order);
            return"orden creada con exito";
        }
        else{
            return "error";
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
