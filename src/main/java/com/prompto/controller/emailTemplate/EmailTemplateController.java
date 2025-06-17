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
        return emailTemplateService.getEmailTemplateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmailTemplateDTO> create(@RequestBody EmailTemplateDTO dto) {
        return ResponseEntity.ok(emailTemplateService.createEmailTemplate(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> update(@PathVariable Long id, @RequestBody EmailTemplateDTO dto) {
        return emailTemplateService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (emailTemplateService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
