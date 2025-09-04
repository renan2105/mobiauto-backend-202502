package com.renan.repository;

import com.renan.domain.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends MongoRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
}
