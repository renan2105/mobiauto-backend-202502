package com.renan.repository;

import com.renan.domain.Loja;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface LojaRepository extends MongoRepository<Loja, UUID> {
}
