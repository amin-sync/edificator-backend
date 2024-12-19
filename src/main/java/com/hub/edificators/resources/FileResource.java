package com.hub.edificators.resources;

import com.hub.edificators.constants.AppConstants;
import com.hub.edificators.dtos.requests.DownloadFileRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileResource {

    @PostMapping("/download")
    public ResponseEntity downloadFile(@RequestBody DownloadFileRequest request) throws MalformedURLException {

        String subDirectory = null;
        if (request.getType().equalsIgnoreCase("note") || request.getType().equalsIgnoreCase("notes")) {
            subDirectory = AppConstants.NOTES_FILE_DIRECTORY;
        } else if (request.getType().equalsIgnoreCase("assignment")) {
            subDirectory = AppConstants.ASSIGNMENT_FILE_DIRECTORY;
        } else {
            subDirectory = AppConstants.UPLOAD_FILE_DIRECTORY;
        }

        Path filePath = Paths.get(AppConstants.BASE_DIRECTORY + "/" + subDirectory).resolve(request.getFileName()).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
