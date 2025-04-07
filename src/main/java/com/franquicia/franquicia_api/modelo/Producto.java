package com.franquicia.franquicia_api.modelo;

import lombok.Data;

@Data
public class Producto {
	private String id;
	private String nombre;
	private int stock;
}
