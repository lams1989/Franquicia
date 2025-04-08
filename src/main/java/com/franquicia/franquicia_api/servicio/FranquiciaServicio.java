package com.franquicia.franquicia_api.servicio;

import org.springframework.stereotype.Service;

import com.franquicia.franquicia_api.modelo.Franquicia;
import com.franquicia.franquicia_api.modelo.Producto;
import com.franquicia.franquicia_api.modelo.Sucursal;
import com.franquicia.franquicia_api.repositorio.FranquiciaRepositorio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranquiciaServicio {

	private static final String LA_FRANQUICIA_O_SU_NOMBRE_NO_PUEDEN_SER_NULOS = "La franquicia o su nombre no pueden ser nulos";
	private static final String FRANQUICIA_NO_ENCONTRADA = "Franquicia no encontrada";
	private static final String LA_SUCURSAL_O_SU_NOMBRE_NO_PUEDEN_SER_NULOS = "La sucursal o su nombre no pueden ser nulos";
	private static final String EL_PRODUCTO_O_SU_NOMBRE_NO_PUEDEN_SER_NULOS = "El producto o su nombre no pueden ser nulos";
	private static final String SUCURSAL_NO_ENCONTRADA = "Sucursal no encontrada";
	private static final String PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";
	private static final String NO_HAY_PRODUCTOS_EN_ESTA_FRANQUICIA = "No hay productos en esta franquicia";

	private final FranquiciaRepositorio repositorio;

	public FranquiciaServicio(FranquiciaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	public Flux<Franquicia> obtenerTodasLasFranquicias() {
		return repositorio.findAll();
	}

	public Mono<Franquicia> crearFranquicia(Franquicia franquicia) {
		if (franquicia == null || franquicia.getNombre() == null) {
			return Mono.error(new IllegalArgumentException(LA_FRANQUICIA_O_SU_NOMBRE_NO_PUEDEN_SER_NULOS));
		}
		return repositorio.save(franquicia);
	}

	public Mono<Franquicia> obtenerFranquiciaPorId(String id) {
		return repositorio.findById(id).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}

	public Mono<Franquicia> agregarSucursal(String franquiciaId, Sucursal sucursal) {
		if (sucursal == null || sucursal.getNombre() == null) {
			return Mono.error(new IllegalArgumentException(LA_SUCURSAL_O_SU_NOMBRE_NO_PUEDEN_SER_NULOS));
		}
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().add(sucursal);
			return repositorio.save(franquicia);
		}).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}

	public Mono<Franquicia> agregarProductoASucursal(String franquiciaId, String sucursalId, Producto producto) {
		if (producto == null || producto.getNombre() == null) {
			return Mono.error(new IllegalArgumentException(EL_PRODUCTO_O_SU_NOMBRE_NO_PUEDEN_SER_NULOS));
		}
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresentOrElse(sucursal -> sucursal.getProductos().add(producto), () -> {
						throw new RuntimeException(SUCURSAL_NO_ENCONTRADA);
					});
			return repositorio.save(franquicia);
		}).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}

	public Mono<Franquicia> eliminarProductoDeSucursal(String franquiciaId, String sucursalId, String productoId) {
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.getProductos()
							.removeIf(producto -> producto.getId().equals(productoId)));
			return repositorio.save(franquicia);
		}).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}

	public Mono<Franquicia> modificarStockDeProducto(String franquiciaId, String sucursalId, String productoId,
			int nuevoStock) {
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.getProductos().stream()
							.filter(producto -> producto.getId().equals(productoId)).findFirst()
							.ifPresentOrElse(producto -> producto.setStock(nuevoStock), () -> {
								throw new RuntimeException(PRODUCTO_NO_ENCONTRADO);
							}));
			return repositorio.save(franquicia);
		}).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}

	public Mono<Producto> obtenerProductoConMayorStock(String franquiciaId) {
		return repositorio.findById(franquiciaId)
				.flatMapMany(franquicia -> Flux.fromIterable(franquicia.getSucursales()))
				.flatMap(sucursal -> Flux.fromIterable(sucursal.getProductos()))
				.sort((p1, p2) -> Integer.compare(p2.getStock(), p1.getStock())).next()
				.switchIfEmpty(Mono.error(new RuntimeException(NO_HAY_PRODUCTOS_EN_ESTA_FRANQUICIA)));
	}

	public Mono<Franquicia> actualizarNombreFranquicia(String id, String nuevoNombre) {
		return repositorio.findById(id).flatMap(franquicia -> {
			franquicia.setNombre(nuevoNombre);
			return repositorio.save(franquicia);
		}).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}

	public Mono<Franquicia> actualizarNombreSucursal(String id, String sucursalId, String nuevoNombre) {
		return repositorio.findById(id).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresentOrElse(sucursal -> sucursal.setNombre(nuevoNombre), () -> {
						throw new RuntimeException(SUCURSAL_NO_ENCONTRADA);
					});
			return repositorio.save(franquicia);
		}).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}

	public Mono<Franquicia> actualizarNombreProducto(String id, String sucursalId, String productoId,
			String nuevoNombre) {
		return repositorio.findById(id).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.getProductos().stream()
							.filter(producto -> producto.getId().equals(productoId)).findFirst()
							.ifPresent(producto -> producto.setNombre(nuevoNombre)));
			return repositorio.save(franquicia);
		}).switchIfEmpty(Mono.error(new RuntimeException(FRANQUICIA_NO_ENCONTRADA)));
	}
}
