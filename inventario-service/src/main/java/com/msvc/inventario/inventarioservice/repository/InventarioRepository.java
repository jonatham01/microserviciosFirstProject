package com.msvc.inventario.inventarioservice.repository;

import com.msvc.inventario.inventarioservice.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface InventarioRepository extends JpaRepository<Inventario,Long> {

    //importan ver el In al final
    List<Inventario> findByCodigoSkuIn(List<String> codigoSku);

    //Optional<Inventario> findByCodigoSku(String codigoSku);
}
