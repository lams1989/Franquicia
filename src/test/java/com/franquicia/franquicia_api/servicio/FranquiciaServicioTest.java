package com.franquicia.franquicia_api.servicio;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.franquicia.franquicia_api.modelo.Franquicia;
import com.franquicia.franquicia_api.modelo.Producto;
import com.franquicia.franquicia_api.modelo.Sucursal;
import com.franquicia.franquicia_api.repositorio.FranquiciaRepositorio;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class FranquiciaServicioTest {

	@MockBean
	private FranquiciaRepositorio repositorio;

	@Autowired
	private FranquiciaServicio servicio;

	private Franquicia franquicia;

	@BeforeEach
	public void setup() {
		Producto producto = new Producto();
		producto.setId("prod1");
		producto.setNombre("Producto A");
		producto.setStock(50);

		Sucursal sucursal = new Sucursal();
		sucursal.setId("suc1");
		sucursal.setNombre("Sucursal A");
		sucursal.setProductos(new ArrayList<>(List.of(producto)));

		franquicia = new Franquicia();
		franquicia.setId("franq1");
		franquicia.setNombre("Franquicia 1");
		franquicia.setSucursales(new ArrayList<>(List.of(sucursal)));

		when(repositorio.findById(eq("franq1"))).thenReturn(Mono.just(franquicia));
		when(repositorio.save(any(Franquicia.class))).thenReturn(Mono.just(franquicia));
	}

	@Test
	void testCrearFranquicia() {
		Mono<Franquicia> result = servicio.crearFranquicia(franquicia);

		StepVerifier.create(result).expectNextMatches(f -> f.getNombre().equals("Franquicia 1")).verifyComplete();
	}

	@Test
	void testObtenerFranquiciaPorId() {
		Mono<Franquicia> result = servicio.obtenerFranquiciaPorId("franq1");

		StepVerifier.create(result).expectNextMatches(f -> f.getId().equals("franq1")).verifyComplete();
	}

	@Test
	void testAgregarSucursal() {
		Sucursal nuevaSucursal = new Sucursal();
		nuevaSucursal.setId("suc2");
		nuevaSucursal.setNombre("Sucursal B");

		Mono<Franquicia> result = servicio.agregarSucursal("franq1", nuevaSucursal);

		StepVerifier.create(result).expectNextMatches(f -> f.getSucursales().size() == 2).verifyComplete();
	}

	@Test
	void testModificarStockDeProducto() {
		Mono<Franquicia> result = servicio.modificarStockDeProducto("franq1", "suc1", "prod1", 100);

		StepVerifier.create(result)
				.expectNextMatches(f -> f.getSucursales().get(0).getProductos().get(0).getStock() == 100)
				.verifyComplete();
	}

	@Test
	void testObtenerProductoConMayorStock() {
		Producto otroProducto = new Producto();
		otroProducto.setId("prod2");
		otroProducto.setNombre("Producto B");
		otroProducto.setStock(200);

		franquicia.getSucursales().get(0).getProductos().add(otroProducto);

		when(repositorio.findById("franq1")).thenReturn(Mono.just(franquicia));

		Mono<Producto> result = servicio.obtenerProductoConMayorStock("franq1");

		StepVerifier.create(result).expectNextMatches(p -> p.getNombre().equals("Producto B")).verifyComplete();
	}
}
