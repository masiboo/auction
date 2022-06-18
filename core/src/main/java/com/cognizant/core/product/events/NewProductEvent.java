package com.cognizant.core.product.events;

import com.cognizant.core.models.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewProductEvent {
    private String id;
    private Product product;
}
