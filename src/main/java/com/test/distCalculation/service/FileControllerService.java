package com.test.distCalculation.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileControllerService {
    String handleFileUpload(MultipartFile file);
}
