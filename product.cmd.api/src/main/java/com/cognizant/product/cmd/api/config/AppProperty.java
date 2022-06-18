package com.cognizant.product.cmd.api.config;

import com.cognizant.product.cmd.api.exceptoins.StorageException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Configuration
@ConfigurationProperties( prefix = "seller")
public class AppProperty {

    private String upload_dir;

    public static String UPLOAD_DIR;

    public static Path uploadPath;

    public void init(){
        AppProperty.UPLOAD_DIR = upload_dir;
        makeUploadPath();
    }

    private static void makeUploadPath(){
        uploadPath = Paths.get(AppProperty.UPLOAD_DIR);
        try {
            Files.createDirectories(uploadPath);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

/*    @Bean
    public AuctionMessageQueueConfig mQSellerConfig(){
        return new AuctionMessageQueueConfig();
    }*/
}
