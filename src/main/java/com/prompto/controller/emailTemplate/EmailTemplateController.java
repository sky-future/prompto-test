package com.prompto.controller.emailTemplate;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.service.impl.EmailTemplateServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@AllArgsConstructor
public class EmailTemplateController {

    @Autowired
    EmailTemplateServiceImpl emailTemplateServiceImpl;

    @GetMapping
    public List<EmailTemplateDTO> getAll() {
        return emailTemplateServiceImpl.getAllEmailTemplates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> getById(@PathVariable Long id) {
        EmailTemplateDTO dto = emailTemplateServiceImpl.getEmailTemplateById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EmailTemplateDTO> create(@RequestBody EmailTemplateDTO dto) {
        return ResponseEntity.ok(emailTemplateServiceImpl.createEmailTemplate(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> update(@PathVariable Long id, @RequestBody EmailTemplateDTO dto) {
        EmailTemplateDTO updated = emailTemplateServiceImpl.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        emailTemplateServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}
