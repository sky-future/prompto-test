package com.prompto.dto.emailTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

// This class is a Data Transfer Object (DTO) for Email Template.
@Getter
@Setter
@Schema(description = "Represents an email template")
public class EmailTemplateDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique template identifier")
    private Long id;

    @Schema(description = "Template name", example = "Newsletter Template")
    private String name;

    @Schema(description = "Email subject line", example = "Monthly Newsletter")
    private String subject;

    @Schema(description = "HTML content of the email")
    private String bodyHtml;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Creation date")
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Last modification date")
    private LocalDateTime updatedAt;
}