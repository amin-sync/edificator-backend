package com.hub.edificators.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DownloadFileRequest {
    private String fileName;
    private String type;
}
