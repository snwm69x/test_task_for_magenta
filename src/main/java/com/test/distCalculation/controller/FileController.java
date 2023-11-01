package com.test.distCalculation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.distCalculation.service.FileControllerService;

@Controller
public class FileController {


    private final FileControllerService fileControllerService;

    public FileController(FileControllerService fileControllerService) {
        this.fileControllerService = fileControllerService;
    }

    @GetMapping("/file")
    public String getFileView() {
        return "file";
    }

    @PostMapping("/file/upload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        return fileControllerService.handleFileUpload(file);
    }
}
