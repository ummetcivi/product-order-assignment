package com.ummetcivi.productorderassignment.data.jpa;

import com.ummetcivi.productorderassignment.data.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {

}
