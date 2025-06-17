package com.prompto.service.emailTemplateService;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.exception.ResourceNotFoundException;
import com.prompto.model.emailTemplate.EmailTemplate;
import com.prompto.repository.emailTemplate.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;

    public List<EmailTemplateDTO> getAllEmailTemplates() {
        return emailTemplateRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EmailTemplateDTO getEmailTemplateById(Long id) {
        return toDTO(emailTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email template with ID " + id + " not found")));
    }

    public EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
        emailTemplateDTO.setId(null); // Ensure ID is null for new entities
        EmailTemplate entity = toEntity(emailTemplateDTO);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return toDTO(emailTemplateRepository.save(entity));
    }

    public EmailTemplateDTO update(Long emailTemplateId, EmailTemplateDTO emailTemplateDTO) {
        EmailTemplate existing = emailTemplateRepository.findById(emailTemplateId)
                .orElseThrow(() -> new ResourceNotFoundException("Email template with ID " + emailTemplateId + " not found"));

        existing.setName(emailTemplateDTO.getName());
        existing.setSubject(emailTemplateDTO.getSubject());
        existing.setBodyHtml(emailTemplateDTO.getBodyHtml());
        existing.setUpdatedAt(LocalDateTime.now());

        return toDTO(emailTemplateRepository.save(existing));
    }

    public void delete(Long emailTemplateId) {
        if (!emailTemplateRepository.existsById(emailTemplateId)) {
            throw new ResourceNotFoundException("Email template with ID " + emailTemplateId + "not found");
        }
        emailTemplateRepository.deleteById(emailTemplateId);
    }



    private EmailTemplateDTO toDTO(EmailTemplate entity) {
        EmailTemplateDTO dto = new EmailTemplateDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSubject(entity.getSubject());
        dto.setBodyHtml(entity.getBodyHtml());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private EmailTemplate toEntity(EmailTemplateDTO dto) {
        EmailTemplate entity = new EmailTemplate();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSubject(dto.getSubject());
        entity.setBodyHtml(dto.getBodyHtml());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
