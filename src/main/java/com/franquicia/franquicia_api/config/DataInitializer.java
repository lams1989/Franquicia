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

	private static final String DATABASE_INITIALIZED_SUCCESSFULLY_WITH_DEMO_DATA = "Database initialized successfully with demo data";
	private static final String ERROR_INITIALIZING_DATABASE = "Error initializing database: ";

	private final FranquiciaRepositorio repositorio;

	public DataInitializer(FranquiciaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@PostConstruct
	public void initDatabase() {
		Mono<Void> deleteAllMono = repositorio.deleteAll();

		if (deleteAllMono == null) {
			deleteAllMono = Mono.empty();
		}

		deleteAllMono.thenMany(Flux.just("Sucursal Principal", "Sucursal Secundaria").map(sucursalNombre -> {
			List<Producto> productos = new ArrayList<>();
			productos.add(new Producto("prod1", "Producto A", 100));
			productos.add(new Producto("prod2", "Producto B", 50));

			return new Sucursal("suc" + (sucursalNombre.equals("Sucursal Principal") ? "1" : "2"), sucursalNombre,
					productos);
		}).collectList().flatMapMany(sucursales -> {
			Franquicia franquicia = new Franquicia("franq1", "Franquicia Demo", sucursales);
			return repositorio.save(franquicia).flux();
		})).doOnComplete(() -> System.out.println(DATABASE_INITIALIZED_SUCCESSFULLY_WITH_DEMO_DATA))
				.doOnError(error -> System.err.println(ERROR_INITIALIZING_DATABASE + error.getMessage())).subscribe();
	}
}
