package com.cognizant.product.cmd.api.service;

import com.cognizant.product.cmd.api.config.AppProperty;
import com.cognizant.product.cmd.api.exceptoins.StorageException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Log
@Service
public class FileSystemStorageService implements StorageService {

	public byte[] store(MultipartFile file, Path uploadPath) {
		try {
			if (file.isEmpty() || uploadPath == null) {
				throw new StorageException("Failed to store empty file for path.");
			}
			Path destinationFile =   AppProperty.uploadPath.resolve(
					Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(AppProperty.uploadPath.toAbsolutePath())) {
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
			return Files.readAllBytes(destinationFile);
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
		//overrideConfig(file.getOriginalFilename(), uploadType);
	}

/*	private void overrideConfig(String fileName, UploadType uploadType){
		if(uploadType == UploadType.ConfigData){
			TestUtility.loadConfigData(FileUtility.loadFileAsString(AppProperty.uploadPath.toAbsolutePath() + File.separator+ fileName));
			log.log(Level.INFO, "ConfigData "+AppProperty.uploadPath.toAbsolutePath() + File.separator+ fileName+ " UPLOADED");
		} else if(uploadType == UploadType.TestData){
			TestUtility.loadTestData(FileUtility.loadFileAsString(AppProperty.uploadPath.toAbsolutePath() + File.separator+ fileName));
			log.log(Level.INFO, "TestData "+AppProperty.uploadPath.toAbsolutePath() + File.separator+ fileName+ " UPLOADED");
		} else if(uploadType == UploadType.AppData){
			PropertyLoader.setResourceFilename(fileName);
			AppProperty.isAppDataUploaded = true;
			log.log(Level.INFO, "AppData "+AppProperty.uploadPath.toAbsolutePath() + File.separator+ fileName+ " UPLOADED");
		}
	}*/

	public static void deleteAll() {
		FileSystemUtils.deleteRecursively(AppProperty.uploadPath.toFile());
	}

}
