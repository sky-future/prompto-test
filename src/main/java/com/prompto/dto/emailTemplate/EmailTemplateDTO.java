package com.prompto.dto.emailTemplate;

import lombok.*;

import java.time.LocalDateTime;

// This class is a Data Transfer Object (DTO) for Email Template.
@Getter
@Setter
public class EmailTemplateDTO {

    private Long id;
    private String name;
    private String subject;
    private String bodyHtml;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
