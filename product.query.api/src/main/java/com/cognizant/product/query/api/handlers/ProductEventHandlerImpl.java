package com.cognizant.product.query.api.handlers;

import com.cognizant.core.product.events.NewProductEvent;
import com.cognizant.core.product.events.ProductRemovedEvent;
import com.cognizant.core.product.events.ProductUpdatedEvent;
import com.cognizant.product.query.api.repositories.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("product-group")
public class ProductEventHandlerImpl implements ProductEventHandler {

    private final ProductRepository productRepository;

    @Autowired
    public ProductEventHandlerImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    @Override
    public void on(NewProductEvent event) {
        productRepository.save(event.getProduct());
    }

    @EventHandler
    @Override
    public void on(ProductUpdatedEvent event) {
        productRepository.save(event.getProduct());
    }

    @EventHandler
    @Override
    public void on(ProductRemovedEvent event) {
        productRepository.deleteById(event.getId());
    }
}
