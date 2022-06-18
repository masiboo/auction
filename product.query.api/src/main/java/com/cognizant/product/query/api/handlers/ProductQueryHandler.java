package com.cognizant.product.query.api.handlers;


import com.cognizant.product.query.api.dto.ProductLookupResponse;
import com.cognizant.product.query.api.queries.FindAllProductQuery;
import com.cognizant.product.query.api.queries.FindProductByIdQuery;
import com.cognizant.product.query.api.queries.SearchProductQuery;

public interface ProductQueryHandler {
    ProductLookupResponse getProductById(FindProductByIdQuery query);
    ProductLookupResponse searchProducts(SearchProductQuery query);
    ProductLookupResponse getAllProducts(FindAllProductQuery query);
}
