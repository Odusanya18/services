package com.partycravings.services.repository;

import com.partycravings.services.model.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServiceRepository extends ReactiveMongoRepository<Service, String> {
    Flux<Service> findByCategoryIdAndIsEnabledIsTrue(String categoryId, Pageable pageable);
    Mono<Long> countByCategoryIdAndIsEnabledIsTrue(String categoryId);
    Mono<Service> findBySlug(String slug);
}
