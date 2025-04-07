package com.franquicia.franquicia_api.repositorio;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.franquicia.franquicia_api.modelo.Franquicia;

@Repository
public interface FranquiciaRepositorio extends ReactiveMongoRepository<Franquicia, String> {
}
