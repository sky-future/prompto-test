package com.prompto.service.impl;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.dto.templateStatistics.EmailTemplateStatisticDTO;
import com.prompto.exception.ResourceNotFoundException;
import com.prompto.model.campaign.Campaign;
import com.prompto.model.emailTemplate.EmailTemplate;
import com.prompto.repository.campaign.CampaignRepository;
import com.prompto.repository.emailTemplate.EmailTemplateRepository;
import com.prompto.service.emailTemplateService.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {


    private final EmailTemplateRepository emailTemplateRepository;
    private final CampaignRepository campaignRepository;


    @Override
    public List<EmailTemplateDTO> getAllEmailTemplates() {
        return emailTemplateRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmailTemplateDTO getEmailTemplateById(Long id) {
        return toDTO(emailTemplateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email template with ID " + id + " not found")));
    }

    @Override
    public EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
        if (emailTemplateDTO == null || emailTemplateDTO.getName() == null || emailTemplateDTO.getName().trim().isEmpty()
                || emailTemplateDTO.getSubject() == null || emailTemplateDTO.getSubject().trim().isEmpty()
                || emailTemplateDTO.getBodyHtml() == null || emailTemplateDTO.getBodyHtml().trim().isEmpty()) {
            throw new IllegalArgumentException("Name, subject and body HTML are required");
        }

        emailTemplateDTO.setId(null);
        EmailTemplate entity = new EmailTemplate();

        entity.setName(emailTemplateDTO.getName());
        entity.setSubject(emailTemplateDTO.getSubject());
        entity.setBodyHtml(emailTemplateDTO.getBodyHtml());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);

        return toDTO(emailTemplateRepository.save(entity));
    }

    @Override
    public EmailTemplateDTO update(Long emailTemplateId, EmailTemplateDTO emailTemplateDTO) {
        EmailTemplate existing = emailTemplateRepository.findById(emailTemplateId)
                .orElseThrow(() -> new ResourceNotFoundException("Email template with ID " + emailTemplateId + " not found"));

        existing.setName(emailTemplateDTO.getName());
        existing.setSubject(emailTemplateDTO.getSubject());
        existing.setBodyHtml(emailTemplateDTO.getBodyHtml());
        existing.setUpdatedAt(LocalDateTime.now());

        return toDTO(emailTemplateRepository.save(existing));
    }

    @Override
    public void delete(Long emailTemplateId) {
        if (!emailTemplateRepository.existsById(emailTemplateId)) {
            throw new ResourceNotFoundException("Email template with ID " + emailTemplateId + "not found");
        }
        emailTemplateRepository.deleteById(emailTemplateId);
    }

    @Override
    public List<EmailTemplateDTO> getTemplatesThatHaveBeenUpdatedSince(LocalDateTime since) {
        return emailTemplateRepository.findByUpdatedAtAfter(since)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmailTemplateStatisticDTO getTemplateStatistics(Long templateId) {
        if (!emailTemplateRepository.existsById(templateId)) {
            throw new ResourceNotFoundException("Email template with ID " + templateId + " not found");
        }

        List<Campaign> campaigns = campaignRepository.findByTemplateId(templateId);
        int campaignsUsed = campaigns.size();
        int emailsSent = campaigns.stream()
                .mapToInt(c -> c.getContactIds() != null ? c.getContactIds().size() : 0)
                .sum();

        EmailTemplateStatisticDTO stat = new EmailTemplateStatisticDTO();
        stat.setTemplateId(templateId);
        stat.setCampaignsUsed(campaignsUsed);
        stat.setEmailsSent(emailsSent);
        return stat;
    }

    @Override
    public List<EmailTemplateDTO> getUnusedTemplatesInCampaigns() {
        return emailTemplateRepository.findTemplatesNotUsedInCampaigns()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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
