package com.prompto.dto.campaign;

import com.prompto.model.campaignStatus.CampaignStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Represents an email campaign")
public class CampaignDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique campaign identifier")
    private Long id;

    @Schema(description = "Campaign name")
    @NotBlank(message = "Campaign name is required")
    private String name;

    @Schema(description = "Associated email template ID")
    @NotNull(message = "Template ID is required")
    private Long templateId;

    @Schema(description = "List of target contact IDs")
    @NotEmpty(message = "Contact list cannot be empty")
    private List<Long> contactIds;

    @Schema(description = "Current campaign status")
    private CampaignStatus status = CampaignStatus.DRAFT;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Creation date")
    private LocalDateTime createdAt;

    @Schema(description = "Scheduled sending date")
    private LocalDateTime scheduledAt;
}
