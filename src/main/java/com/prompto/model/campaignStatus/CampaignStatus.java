package com.prompto.model.campaignStatus;

import lombok.Getter;

@Getter
public enum CampaignStatus {
    DRAFT("Draft"),
    SEND("Send");

    private final String status;

    CampaignStatus(String status) {
        this.status = status;
    }

}
