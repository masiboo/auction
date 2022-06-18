package com.cognizant.product.cmd.api.controllers;

import com.cognizant.core.models.Product;
import com.cognizant.product.cmd.api.commands.NewProductCommand;
import com.cognizant.product.cmd.api.config.AppProperty;
import com.cognizant.product.cmd.api.dto.NewProductResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/addProduct")
public class NewProductController {
    private final CommandGateway commandGateway;
    private final AppProperty appProperty;

    @Autowired
    public NewProductController(CommandGateway commandGateway, AppProperty appProperty) {
        this.commandGateway = commandGateway;
        this.appProperty = appProperty;
        this.appProperty.init();
    }

    @PostMapping
    public ResponseEntity<NewProductResponse> registerUser(@Valid @ModelAttribute("product") Product product) {
        MultipartFile file = product.getFile();
        final String absoluteUploadDir = System.getProperty("user.dir") + File.separator + Path.of(AppProperty.UPLOAD_DIR);
        String fileUrl = absoluteUploadDir+ File.separator + file.getOriginalFilename();
        product.setFileUrl(fileUrl);

        NewProductCommand command = NewProductCommand.builder()
                .product(product)
                .build();
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new NewProductResponse(id, "Product successfully registered!"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing new product request for id - " + id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new NewProductResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
