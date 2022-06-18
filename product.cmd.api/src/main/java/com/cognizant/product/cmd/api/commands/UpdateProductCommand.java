package com.cognizant.product.cmd.api.commands;

import com.cognizant.core.models.Product;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateProductCommand {
    @TargetAggregateIdentifier
    private String id;
    @NotNull(message = "no product details were supplied")
    @Valid
    private Product product;
}
