package com.cognizant.product.query.api.repositories;

import com.cognizant.core.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'$or' : [{'name': {$regex : ?0, $options: '1'}}, {'price': {$regex : ?0, $options: '1'}}, {'sellerName': {$regex : ?0, $options: '1'}}]}")
    List<Product> findByFilterRegex(String filter);
}
