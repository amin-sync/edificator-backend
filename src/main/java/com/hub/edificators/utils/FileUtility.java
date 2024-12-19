package com.hub.edificators.utils;

import com.hub.edificators.constants.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileUtility {

    static {
        try {
            initializeDirectories();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize directories", e);
        }
    }

    private static void initializeDirectories() throws IOException {
        for (String subDir : AppConstants.SUBDIRECTORIES) {
            Path directoryPath = Paths.get(AppConstants.BASE_DIRECTORY, subDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
        }
    }

    public static String saveFile(Long referenceId, String directory, MultipartFile file) throws IOException {
        Path directoryPath = Paths.get(AppConstants.BASE_DIRECTORY, directory);

        String fileName = getFileName(referenceId, directory, file);
        Path filePath = directoryPath.resolve(fileName);

        Files.deleteIfExists(filePath);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    private static String getFileName(Long referenceId, String directory, MultipartFile file) {
        return switch (directory) {
            case AppConstants.PROFILE_PIC_DIRECTORY ->
                    referenceId + "_profile" + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            case AppConstants.ASSIGNMENT_FILE_DIRECTORY ->
                    referenceId + "_assignment" + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            case AppConstants.UPLOAD_FILE_DIRECTORY ->
                    referenceId + "_upload" + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            default ->
                    referenceId + "_notes" + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        };
    }

}
