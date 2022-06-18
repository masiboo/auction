package com.cognizant.product.query.api.handlers;

import com.cognizant.product.query.api.dto.ProductLookupResponse;
import com.cognizant.product.query.api.queries.FindAllProductQuery;
import com.cognizant.product.query.api.queries.FindProductByIdQuery;
import com.cognizant.product.query.api.queries.SearchProductQuery;
import com.cognizant.product.query.api.repositories.ProductRepository;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductQueryHandlerImpl implements ProductQueryHandler {
    private final ProductRepository productRepository;

    @Autowired
    public ProductQueryHandlerImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    @Override
    public ProductLookupResponse getProductById(FindProductByIdQuery query) {
        var product = productRepository.findById(query.getId());
        return product.isPresent() ? new ProductLookupResponse(product.get()) : null;
    }

    @QueryHandler
    @Override
    public ProductLookupResponse searchProducts(SearchProductQuery query) {
        var products = new ArrayList<>(productRepository.findByFilterRegex(query.getFilter()));
        return new ProductLookupResponse(products);
    }

    @QueryHandler
    @Override
    public ProductLookupResponse getAllProducts(FindAllProductQuery query) {
        var products = new ArrayList<>(productRepository.findAll());
        return new ProductLookupResponse(products);
    }
}
