package com.prompto.repository.emailTemplate;

import com.prompto.model.emailTemplate.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    // Find all email templates that were updated after a specific date
    List<EmailTemplate> findByUpdatedAtAfter(LocalDateTime updatedSince);

    @Query("""
            SELECT t FROM EmailTemplate t
            WHERE t.id NOT IN (SELECT DISTINCT c.template.id FROM Campaign c)
            """)

    List<EmailTemplate> findTemplatesNotUsedInCampaigns();
}
