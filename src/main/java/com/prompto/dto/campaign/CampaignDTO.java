package com.prompto.dto.campaign;

import com.prompto.model.campaignStatus.CampaignStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CampaignDTO {

    private Long id;
    private String name;
    private Long templateId;
    private List<Long> contactIds;
    private CampaignStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledAt;

}
