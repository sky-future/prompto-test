package com.prompto.dto.campaignStatistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Represents email campaign statistics")
public class CampaignStatisticsDTO {

    @Schema(description = "Campaign identifier")
    private Long campaignId;

    @Schema(description = "Number of emails sent")
    private int sent;

    @Schema(description = "Number of emails delivered")
    private int delivered;

    @Schema(description = "Number of emails opened")
    private int opened;

    @Schema(description = "Number of emails clicked")
    private int clicked;
}
