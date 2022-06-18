package com.cognizant.product.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindProductByIdQuery {
    private String id;
}
