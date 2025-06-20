package com.prompto.model.campaignStatistics;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CampaignStatistics {

    @Id
    private Long campaignId;

    private int sent;
    private int delivered;
    private int opened;
    private int clicked;
}
