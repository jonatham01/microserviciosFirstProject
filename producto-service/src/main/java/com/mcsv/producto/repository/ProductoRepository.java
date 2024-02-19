package com.mcsv.producto.repository;

import com.mcsv.producto.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends MongoRepository<Producto,String> {

}
