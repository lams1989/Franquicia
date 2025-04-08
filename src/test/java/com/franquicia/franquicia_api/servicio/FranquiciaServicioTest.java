package com.franquicia.franquicia_api.servicio;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.franquicia.franquicia_api.FranquiciaApiApplication;
import com.franquicia.franquicia_api.modelo.Franquicia;
import com.franquicia.franquicia_api.modelo.Producto;
import com.franquicia.franquicia_api.modelo.Sucursal;
import com.franquicia.franquicia_api.repositorio.FranquiciaRepositorio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FranquiciaApiApplication.class)
@ActiveProfiles("test")
class FranquiciaServicioTest {

	@MockBean
	private FranquiciaRepositorio repositorio;

	@Autowired
	private FranquiciaServicio servicio;

	private Franquicia franquicia;

	@BeforeEach
	public void setup() {
		Producto producto = new Producto("prod1", "Producto A", 50);
		Sucursal sucursal = new Sucursal("suc1", "Sucursal A", new ArrayList<>(List.of(producto)));

		franquicia = new Franquicia("franq1", "Franquicia 1", new ArrayList<>(List.of(sucursal)));

		when(repositorio.findById("franq1")).thenReturn(Mono.just(franquicia));
		when(repositorio.save(any(Franquicia.class))).thenReturn(Mono.just(franquicia));
		when(repositorio.findAll()).thenReturn(Flux.just(franquicia));
	}

	@Test
	void testObtenerTodasLasFranquicias() {
		Flux<Franquicia> result = servicio.obtenerTodasLasFranquicias();

		StepVerifier.create(result).expectNextMatches(f -> f.getId().equals("franq1")).verifyComplete();
	}

	@Test
	void testCrearFranquicia() {
		Franquicia nuevaFranquicia = new Franquicia("franq2", "Franquicia 2", new ArrayList<>());
		when(repositorio.save(any(Franquicia.class))).thenReturn(Mono.just(nuevaFranquicia));

		Mono<Franquicia> result = servicio.crearFranquicia(nuevaFranquicia);

		StepVerifier.create(result).expectNextMatches(f -> f.getNombre().equals("Franquicia 2")).verifyComplete();
	}

	@Test
	void testCrearFranquiciaConNulo() {
		Mono<Franquicia> result = servicio.crearFranquicia(null);

		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
	}

	@Test
	void testObtenerFranquiciaPorId() {
		Mono<Franquicia> result = servicio.obtenerFranquiciaPorId("franq1");

		StepVerifier.create(result).expectNextMatches(f -> f.getId().equals("franq1")).verifyComplete();
	}

	@Test
	void testObtenerFranquiciaPorIdNoExistente() {
		when(repositorio.findById("franq99")).thenReturn(Mono.empty());

		Mono<Franquicia> result = servicio.obtenerFranquiciaPorId("franq99");

		StepVerifier.create(result).expectError(RuntimeException.class).verify();
	}

	@Test
	void testAgregarSucursal() {
		Sucursal nuevaSucursal = new Sucursal("suc2", "Sucursal B", null);

		Mono<Franquicia> result = servicio.agregarSucursal("franq1", nuevaSucursal);

		StepVerifier.create(result).expectNextMatches(f -> f.getSucursales().size() == 2).verifyComplete();
	}

	@Test
	void testAgregarSucursalNula() {
		Mono<Franquicia> result = servicio.agregarSucursal("franq1", null);

		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
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

		Mono<Producto> result = servicio.obtenerProductoConMayorStock("franq1");

		StepVerifier.create(result).expectNextMatches(p -> p.getNombre().equals("Producto B")).verifyComplete();
	}

	@Test
	void testActualizarNombreFranquicia() {
		Mono<Franquicia> result = servicio.actualizarNombreFranquicia("franq1", "Franquicia Actualizada");

		StepVerifier.create(result).expectNextMatches(f -> f.getNombre().equals("Franquicia Actualizada"))
				.verifyComplete();
	}

	@Test
	void testActualizarNombreSucursal() {
		Mono<Franquicia> result = servicio.actualizarNombreSucursal("franq1", "suc1", "Sucursal Actualizada");

		StepVerifier.create(result)
				.expectNextMatches(f -> f.getSucursales().get(0).getNombre().equals("Sucursal Actualizada"))
				.verifyComplete();
	}

	@Test
	void testActualizarNombreProducto() {
		Mono<Franquicia> result = servicio.actualizarNombreProducto("franq1", "suc1", "prod1", "Producto Actualizado");

		StepVerifier.create(result)
				.expectNextMatches(
						f -> f.getSucursales().get(0).getProductos().get(0).getNombre().equals("Producto Actualizado"))
				.verifyComplete();
	}

	@Test
	void testEliminarProductoDeSucursal() {
		Mono<Franquicia> result = servicio.eliminarProductoDeSucursal("franq1", "suc1", "prod1");

		StepVerifier.create(result).expectNextMatches(f -> f.getSucursales().get(0).getProductos().isEmpty())
				.verifyComplete();
	}
}
