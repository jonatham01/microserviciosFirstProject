package com.mcsv.producto.controller;

import com.mcsv.producto.dto.ProductoRequest;
import com.mcsv.producto.dto.ProductoResponse;
import com.mcsv.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void guardarProducto(@RequestBody ProductoRequest productoRequest){
        productoService.createProducto(productoRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoResponse> listarProductos(){
        return productoService.getAllProductos();
    }
}
