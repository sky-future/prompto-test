package com.prompto.controller.emailTemplate;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.service.emailTemplateService.EmailTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @GetMapping
    @Operation(summary = "Get all email templates")
    public ResponseEntity<List<EmailTemplateDTO>> getAll() {
        return ResponseEntity.ok(emailTemplateService.getAllEmailTemplates());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get email template by ID")
    public ResponseEntity<EmailTemplateDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(emailTemplateService.getEmailTemplateById(id));
    }

    @PostMapping
    @Operation(summary = "Create new email template")
    @ApiResponse(responseCode = "201", description = "Email template created")
    public ResponseEntity<EmailTemplateDTO> create(@RequestBody EmailTemplateDTO template) {
        if (template == null) {
            throw new IllegalArgumentException("Email template cannot be null");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emailTemplateService.createEmailTemplate(template));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing email template")
    public ResponseEntity<EmailTemplateDTO> update(
            @PathVariable Long id,
            @RequestBody EmailTemplateDTO template) {
        return ResponseEntity.ok(emailTemplateService.update(id, template));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete email template")
    @ApiResponse(responseCode = "204", description = "Email template deleted")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        emailTemplateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}