package com.prompto.service.emailTemplateService;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.dto.templateStatistics.EmailTemplateStatisticDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailTemplateService {
    List<EmailTemplateDTO> getAllEmailTemplates();

    EmailTemplateDTO getEmailTemplateById(Long id);

    EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO);

    EmailTemplateDTO update(Long emailTemplateId, EmailTemplateDTO emailTemplateDTO);

    void delete(Long emailTemplateId);

    List<EmailTemplateDTO> getTemplatesThatHaveBeenUpdatedSince(LocalDateTime timestamp);

    EmailTemplateStatisticDTO getTemplateStatistics(Long templateId);

    List<EmailTemplateDTO> getUnusedTemplatesInCampaigns();
}
