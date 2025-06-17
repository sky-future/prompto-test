package com.prompto.repository.emailTemplate;

import com.prompto.model.emailTemplate.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
}
