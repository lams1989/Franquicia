package com.franquicia.franquicia_api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.franquicia.franquicia_api.modelo.Franquicia;
import com.franquicia.franquicia_api.modelo.Producto;
import com.franquicia.franquicia_api.modelo.Sucursal;
import com.franquicia.franquicia_api.repositorio.FranquiciaRepositorio;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataInitializer {

	private final FranquiciaRepositorio repositorio;

	public DataInitializer(FranquiciaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@PostConstruct
	public void initDatabase() {
		Mono<Void> deleteAllMono = repositorio.deleteAll();

		// Verificar si deleteAllMono es null y manejarlo adecuadamente
		if (deleteAllMono == null) {
			deleteAllMono = Mono.empty();
		}

		deleteAllMono.thenMany(Flux.just("Sucursal Principal", "Sucursal Secundaria").map(sucursalNombre -> {
			// Crear productos para cada sucursal
			List<Producto> productos = new ArrayList<>();
			productos.add(new Producto("prod1", "Producto A", 100));
			productos.add(new Producto("prod2", "Producto B", 50));

			// Crear la sucursal con productos
			return new Sucursal("suc" + (sucursalNombre.equals("Sucursal Principal") ? "1" : "2"), sucursalNombre,
					productos);
		}).collectList().flatMapMany(sucursales -> {
			// Crear la franquicia con las sucursales
			Franquicia franquicia = new Franquicia("franq1", "Franquicia Demo", sucursales);
			return repositorio.save(franquicia).flux();
		})).doOnComplete(() -> System.out.println("Database initialized successfully with demo data"))
				.doOnError(error -> System.err.println("Error initializing database: " + error.getMessage()))
				.subscribe();
	}
}
