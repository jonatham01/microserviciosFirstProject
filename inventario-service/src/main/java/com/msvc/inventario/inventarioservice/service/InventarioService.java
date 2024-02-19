package com.msvc.inventario.inventarioservice.service;

import com.msvc.inventario.inventarioservice.dto.InventarioResponse;
import com.msvc.inventario.inventarioservice.repository.InventarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {

    @Autowired
    protected InventarioRepository inventarioRepository;

    @Transactional()
    public List<InventarioResponse> isInStock(List<String> codigoSku){
        return inventarioRepository.findByCodigoSkuIn(codigoSku).stream()
                .map(inventario ->
                        InventarioResponse.builder()
                                .codigoSku(inventario.getCodigoSku())
                                .isInStock(inventario.getCantidad()>0)
                                .build()
                ).collect(Collectors.toList());
    }

    /*
    @Transactional()
    public boolean isInStock(String codigoSku){

        return inventarioRepository.findByCodigoSku(codigoSku).isPresent();
    }
     */
}
