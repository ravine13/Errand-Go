package com.errandGo.backend.controller;

import com.errandGo.backend.dto.ApplicationView;
import com.errandGo.backend.service.ErrandBoyApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user/errand-boy-applications")
@RequiredArgsConstructor
public class ErrandBoyApplicationUserController {

    private final ErrandBoyApplicationService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationView> submit(
            @RequestParam String fullName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam String phoneNumber,
            @RequestPart("idDocument") MultipartFile idDocument,
            @RequestPart("goodConduct") MultipartFile goodConduct,
            @RequestPart("selfie") MultipartFile selfie,
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.submit(auth.getName(), fullName, dateOfBirth, phoneNumber,
                        idDocument, goodConduct, selfie));
    }

    @GetMapping("/me")
    public List<ApplicationView> mine(Authentication auth) {
        return service.mine(auth.getName());
    }
}