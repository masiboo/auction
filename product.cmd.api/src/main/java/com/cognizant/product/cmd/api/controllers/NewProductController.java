package com.cognizant.product.cmd.api.controllers;

import com.cognizant.core.configuration.AppMessageQueueConfig;
import com.cognizant.core.models.Product;
import com.cognizant.product.cmd.api.commands.NewProductCommand;
import com.cognizant.product.cmd.api.config.AppProperty;
import com.cognizant.product.cmd.api.dto.NewProductResponse;
import com.cognizant.product.cmd.api.service.StorageService;
import com.cognizant.product.cmd.api.util.MessageUtil;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/addProduct")
public class NewProductController {
    private final CommandGateway commandGateway;
    private final AppProperty appProperty;
    private final RabbitTemplate template;
    private final StorageService storageService;
    private final AppMessageQueueConfig appMessageQueueConfig;

    @Autowired
    public NewProductController(CommandGateway commandGateway, AppProperty appProperty, RabbitTemplate template, StorageService storageService, AppMessageQueueConfig appMessageQueueConfig) {
        this.commandGateway = commandGateway;
        this.appProperty = appProperty;
        this.template = template;
        this.storageService = storageService;
        this.appMessageQueueConfig = appMessageQueueConfig;
        this.appProperty.init();
    }

    @PostMapping
    public ResponseEntity<NewProductResponse> registerUser(@Valid @ModelAttribute("product") Product product) {
        MultipartFile file = product.getFile();
        final String absoluteUploadDir = System.getProperty("user.dir") + File.separator + Path.of(AppProperty.UPLOAD_DIR);
        String fileUrl = absoluteUploadDir+ File.separator + file.getOriginalFilename();
        product.setFileUrl(fileUrl);

        byte[] fileByte = storageService.store(file, Paths.get(absoluteUploadDir));
        template.convertAndSend(appMessageQueueConfig.EXCHANGE,
                appMessageQueueConfig.ROUTING_KEY, MessageUtil.makeCustomMessage(fileByte));


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
