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

	private final FranquiciaRepositorio repositorio;

	public FranquiciaServicio(FranquiciaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	public Flux<Franquicia> obtenerTodasLasFranquicias() {
		return repositorio.findAll();
	}

	public Mono<Franquicia> crearFranquicia(Franquicia franquicia) {
		return repositorio.save(franquicia);
	}

	public Mono<Franquicia> obtenerFranquiciaPorId(String id) {
		return repositorio.findById(id);
	}

	public Mono<Franquicia> agregarSucursal(String franquiciaId, Sucursal sucursal) {
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().add(sucursal);
			return repositorio.save(franquicia);
		});
	}

	public Mono<Franquicia> agregarProductoASucursal(String franquiciaId, String sucursalId, Producto producto) {
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.getProductos().add(producto));
			return repositorio.save(franquicia);
		});
	}

	public Mono<Franquicia> eliminarProductoDeSucursal(String franquiciaId, String sucursalId, String productoId) {
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.getProductos()
							.removeIf(producto -> producto.getId().equals(productoId)));
			return repositorio.save(franquicia);
		});
	}

	public Mono<Franquicia> modificarStockDeProducto(String franquiciaId, String sucursalId, String productoId,
			int nuevoStock) {
		return repositorio.findById(franquiciaId).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.getProductos().stream()
							.filter(producto -> producto.getId().equals(productoId)).findFirst()
							.ifPresent(producto -> producto.setStock(nuevoStock)));
			return repositorio.save(franquicia);
		});
	}

	public Mono<Producto> obtenerProductoConMayorStock(String franquiciaId) {
		return repositorio.findById(franquiciaId)
				.flatMapMany(franquicia -> Flux.fromIterable(franquicia.getSucursales()))
				.flatMap(sucursal -> Flux.fromIterable(sucursal.getProductos()))
				.sort((p1, p2) -> Integer.compare(p2.getStock(), p1.getStock())).next();
	}

	public Mono<Franquicia> actualizarNombreFranquicia(String id, String nuevoNombre) {
		return repositorio.findById(id).flatMap(franquicia -> {
			franquicia.setNombre(nuevoNombre);
			return repositorio.save(franquicia);
		});
	}

	public Mono<Franquicia> actualizarNombreSucursal(String id, String sucursalId, String nuevoNombre) {
		return repositorio.findById(id).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.setNombre(nuevoNombre));
			return repositorio.save(franquicia);
		});
	}

	public Mono<Franquicia> actualizarNombreProducto(String id, String sucursalId, String productoId,
			String nuevoNombre) {
		return repositorio.findById(id).flatMap(franquicia -> {
			franquicia.getSucursales().stream().filter(sucursal -> sucursal.getId().equals(sucursalId)).findFirst()
					.ifPresent(sucursal -> sucursal.getProductos().stream()
							.filter(producto -> producto.getId().equals(productoId)).findFirst()
							.ifPresent(producto -> producto.setNombre(nuevoNombre)));
			return repositorio.save(franquicia);
		});
	}
}
