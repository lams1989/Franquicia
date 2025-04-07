package com.franquicia.franquicia_api.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquicia.franquicia_api.modelo.Franquicia;
import com.franquicia.franquicia_api.modelo.Producto;
import com.franquicia.franquicia_api.modelo.Sucursal;
import com.franquicia.franquicia_api.servicio.FranquiciaServicio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
public class FranquiciaControlador {

	private final FranquiciaServicio servicio;

	@Autowired
	public FranquiciaControlador(FranquiciaServicio servicio) {
		this.servicio = servicio;
	}

	@GetMapping
	public Flux<Franquicia> listarFranquicias() {
		return servicio.obtenerTodasLasFranquicias();
	}

	@PostMapping
	public Mono<Franquicia> crearFranquicia(@RequestBody Franquicia franquicia) {
		return servicio.crearFranquicia(franquicia);
	}

	@GetMapping("/{id}")
	public Mono<Franquicia> obtenerFranquiciaPorId(@PathVariable String id) {
		return servicio.obtenerFranquiciaPorId(id);
	}

	@PostMapping("/{id}/sucursales")
	public Mono<Franquicia> agregarSucursal(@PathVariable String id, @RequestBody Sucursal sucursal) {
		return servicio.agregarSucursal(id, sucursal);
	}

	@PostMapping("/{id}/sucursales/{sucursalId}/productos")
	public Mono<Franquicia> agregarProductoASucursal(@PathVariable String id, @PathVariable String sucursalId,
			@RequestBody Producto producto) {
		return servicio.agregarProductoASucursal(id, sucursalId, producto);
	}

	@DeleteMapping("/{id}/sucursales/{sucursalId}/productos/{productoId}")
	public Mono<Franquicia> eliminarProductoDeSucursal(@PathVariable String id, @PathVariable String sucursalId,
			@PathVariable String productoId) {
		return servicio.eliminarProductoDeSucursal(id, sucursalId, productoId);
	}

	@PatchMapping("/{id}/sucursales/{sucursalId}/productos/{productoId}/stock")
	public Mono<Franquicia> modificarStockDeProducto(@PathVariable String id, @PathVariable String sucursalId,
			@PathVariable String productoId, @RequestBody int nuevoStock) {
		return servicio.modificarStockDeProducto(id, sucursalId, productoId, nuevoStock);
	}

	@GetMapping("/{id}/productoMayorStock")
	public Mono<Producto> obtenerProductoConMayorStock(@PathVariable String id) {
		return servicio.obtenerProductoConMayorStock(id);
	}

	@PatchMapping("/{id}/nombre")
	public Mono<Franquicia> actualizarNombreFranquicia(@PathVariable String id, @RequestBody String nuevoNombre) {
		return servicio.actualizarNombreFranquicia(id, nuevoNombre);
	}

	@PatchMapping("/{id}/sucursales/{sucursalId}/nombre")
	public Mono<Franquicia> actualizarNombreSucursal(@PathVariable String id, @PathVariable String sucursalId,
			@RequestBody String nuevoNombre) {
		return servicio.actualizarNombreSucursal(id, sucursalId, nuevoNombre);
	}

	@PatchMapping("/{id}/sucursales/{sucursalId}/productos/{productoId}/nombre")
	public Mono<Franquicia> actualizarNombreProducto(@PathVariable String id, @PathVariable String sucursalId,
			@PathVariable String productoId, @RequestBody String nuevoNombre) {
		return servicio.actualizarNombreProducto(id, sucursalId, productoId, nuevoNombre);
	}
}
