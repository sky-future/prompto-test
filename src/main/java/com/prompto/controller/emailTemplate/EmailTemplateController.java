package com.prompto.controller.emailTemplate;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.service.emailTemplateService.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @GetMapping
    public List<EmailTemplateDTO> getAll() {
        return emailTemplateService.getAllEmailTemplates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> getById(@PathVariable Long id) {
        EmailTemplateDTO dto = emailTemplateService.getEmailTemplateById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EmailTemplateDTO> create(@RequestBody EmailTemplateDTO dto) {
        return ResponseEntity.ok(emailTemplateService.createEmailTemplate(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> update(@PathVariable Long id, @RequestBody EmailTemplateDTO dto) {
        EmailTemplateDTO updated = emailTemplateService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        emailTemplateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
