package com.franquicia.franquicia_api.modelo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {
	private String id;
	private String nombre;
	@Field("productos")
	private List<Producto> productos;
}
