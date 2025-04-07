package com.franquicia.franquicia_api.modelo;

import java.util.List;

import lombok.Data;

@Data
public class Sucursal {
	private String id;
	private String nombre;
	private List<Producto> productos;
}
