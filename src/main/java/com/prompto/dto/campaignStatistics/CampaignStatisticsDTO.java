package com.prompto.dto.campaignStatistics;

import lombok.Data;

@Data
public class CampaignStatisticsDTO {
    private Long campaignId;
    private int sent;
    private int delivered;
    private int opened;
    private int clicked;
}
