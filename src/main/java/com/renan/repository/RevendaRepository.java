package com.renan.repository;

import com.renan.domain.Revenda;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RevendaRepository extends MongoRepository<Revenda, UUID> {
}
