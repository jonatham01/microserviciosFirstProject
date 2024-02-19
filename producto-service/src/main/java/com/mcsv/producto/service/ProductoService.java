package com.mcsv.producto.service;

import com.mcsv.producto.dto.ProductoRequest;
import com.mcsv.producto.dto.ProductoResponse;
import com.mcsv.producto.model.Producto;
import com.mcsv.producto.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public void createProducto( ProductoRequest productoRequest){
        Producto producto = Producto.builder()
                .nombre(productoRequest.getNombre())
                .description(productoRequest.getDescription())
                .precio(productoRequest.getPrecio())
                .build();
        productoRepository.save(producto);
        log.info("producto{} ha sido guardado con exito", producto.getId());
    }

    public List<ProductoResponse> getAllProductos(){
        List<Producto> productos = productoRepository.findAll() ;
        return productos.stream().map(this::mapToProductoResponse).collect(Collectors.toList());
    }

    private ProductoResponse mapToProductoResponse(Producto producto){
        return  ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .description(producto.getDescription())
                .precio(producto.getPrecio())
                .build();
    }

}
