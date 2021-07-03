package com.ummetcivi.productorderassignment.data.jpa;

import com.ummetcivi.productorderassignment.data.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    @Query("{createdAt: {$gte: ?0, $lte: ?1}}")
    Page<OrderEntity> findAllByCreatedAtBetween(final Instant start, final Instant end, final Pageable pageable);
}
