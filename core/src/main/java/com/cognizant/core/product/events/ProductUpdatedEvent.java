package com.cognizant.core.product.events;

import com.cognizant.core.models.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdatedEvent {
    private String id;
    private Product product;
}
