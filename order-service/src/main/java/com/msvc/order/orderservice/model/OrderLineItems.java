package com.msvc.order.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="order_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String codigoSku;
    private BigDecimal precio;
    private Integer cantidad;
}
