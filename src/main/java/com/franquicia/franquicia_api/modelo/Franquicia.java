package com.franquicia.franquicia_api.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "franquicias")
public class Franquicia {
	@Id
	private String id;
	private String nombre;
	private List<Sucursal> sucursales;
}
