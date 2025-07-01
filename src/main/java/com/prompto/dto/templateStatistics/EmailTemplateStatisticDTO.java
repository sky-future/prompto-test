package com.prompto.dto.templateStatistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Represents email template statistics")
public class EmailTemplateStatisticDTO {

    @Schema(description = "Template identifier")
    private Long templateId;

    @Schema(description = "Number of campaigns using this template")
    private int campaignsUsed;

    @Schema(description = "Number of emails sent using this template")
    private int emailsSent;
}
