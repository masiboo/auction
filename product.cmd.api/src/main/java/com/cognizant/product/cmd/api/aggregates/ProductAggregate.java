package com.cognizant.product.cmd.api.aggregates;

import com.cognizant.core.models.Product;
import com.cognizant.core.product.events.NewProductEvent;
import com.cognizant.core.product.events.ProductRemovedEvent;
import com.cognizant.core.product.events.ProductUpdatedEvent;
import com.cognizant.core.user.events.UserRemovedEvent;
import com.cognizant.product.cmd.api.commands.NewProductCommand;
import com.cognizant.product.cmd.api.commands.RemoveProductCommand;
import com.cognizant.product.cmd.api.commands.UpdateProductCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String id;
    private Product product;

    @CommandHandler
    public ProductAggregate(NewProductCommand command) {
        var newProduct = command.getProduct();
        newProduct.setId(command.getId());

        var event = NewProductEvent.builder()
                .id(command.getId())
                .product(newProduct)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateProductCommand command) {
        var updatedProduct = command.getProduct();
        updatedProduct.setId(command.getId());

        var event = ProductUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .product(updatedProduct)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveProductCommand command) {
        var event = new ProductRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(NewProductEvent event) {
        this.id = event.getId();
        this.product = event.getProduct();
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.product = event.getProduct();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
