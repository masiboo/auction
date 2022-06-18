package com.cognizant.product.query.api.handlers;

import com.cognizant.core.product.events.NewProductEvent;
import com.cognizant.core.product.events.ProductRemovedEvent;
import com.cognizant.core.product.events.ProductUpdatedEvent;

public interface ProductEventHandler {
    void on(NewProductEvent event);
    void on(ProductUpdatedEvent event);
    void on(ProductRemovedEvent event);
}
