package com.errandGo.backend.controller;

import com.errandGo.backend.dto.ApplicationView;
import com.errandGo.backend.service.ErrandBoyApplicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import model.ApplicationStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/errand-boy-applications")
@RequiredArgsConstructor
public class ErrandBoyApplicationAdminController {

    private final ErrandBoyApplicationService service;

    public record RejectRequest(@NotBlank @Size(max = 500) String reason) {}

    @GetMapping
    public List<ApplicationView> list(@RequestParam(defaultValue = "PENDING") ApplicationStatus status) {
        return service.list(status);
    }

    @GetMapping("/{id}/documents/{type}")
    public ResponseEntity<Resource> document(@PathVariable Long id, @PathVariable String type) {
        Resource file = service.document(id, type);
        String name = file.getFilename() == null ? "" : file.getFilename();
        MediaType mt = name.endsWith(".pdf") ? MediaType.APPLICATION_PDF
                : name.endsWith(".png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
        return ResponseEntity.ok()
                .contentType(mt)
                .header("X-Content-Type-Options", "nosniff")
                .header("Cache-Control", "no-store")
                .body(file);
    }

    @PostMapping("/{id}/approve")
    public ApplicationView approve(@PathVariable Long id, Authentication auth) {
        return service.approve(id, auth.getName());
    }

    @PostMapping("/{id}/reject")
    public ApplicationView reject(@PathVariable Long id,
                                  @Valid @RequestBody RejectRequest body,
                                  Authentication auth) {
        return service.reject(id, body.reason(), auth.getName());
    }
}