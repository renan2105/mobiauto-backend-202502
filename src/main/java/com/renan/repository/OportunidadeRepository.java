package com.renan.repository;

import com.renan.domain.Oportunidade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface OportunidadeRepository extends MongoRepository<Oportunidade, UUID> {
    List<Oportunidade> findByIdLoja(UUID idLoja);
}
