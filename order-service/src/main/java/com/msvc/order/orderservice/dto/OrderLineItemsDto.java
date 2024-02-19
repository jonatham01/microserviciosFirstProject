package com.msvc.order.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderLineItemsDto {

    private Long id;
    private String codigoSku;
    private BigDecimal precio;
    private Integer cantidad;

}
