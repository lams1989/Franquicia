package com.franquicia.franquicia_api.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	private String id;
	private String nombre;
	private int stock;
}
