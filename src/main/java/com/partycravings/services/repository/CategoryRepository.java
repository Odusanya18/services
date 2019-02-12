package com.partycravings.services.repository;

import com.partycravings.services.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
    Mono<Category> findBySlug(String slug);
}
