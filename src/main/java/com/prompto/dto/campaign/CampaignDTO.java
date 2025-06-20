package com.prompto.dto.campaign;

import com.prompto.model.campaignStatus.CampaignStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CampaignDTO {

    // Hidden from the user, used internally
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String name;
    private Long templateId;
    private List<Long> contactIds;
    private CampaignStatus status;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime scheduledAt;

}
