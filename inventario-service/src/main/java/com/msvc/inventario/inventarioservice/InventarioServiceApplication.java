package com.msvc.inventario.inventarioservice;

import com.msvc.inventario.inventarioservice.entity.Inventario;
import com.msvc.inventario.inventarioservice.repository.InventarioRepository;
import com.msvc.inventario.inventarioservice.service.InventarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}


	public CommandLineRunner leadData(InventarioRepository inventarioRepository){
		return args -> {
			Inventario inventario = new Inventario();
			inventario.setCodigoSku("iphone_12");
			inventario.setCantidad(100);

			Inventario inventario2 = new Inventario();
			inventario2.setCodigoSku("iphone_12_blue");
			inventario2.setCantidad(0);

			inventarioRepository.save(inventario);
			inventarioRepository.save(inventario2);
		};
	 }
}
