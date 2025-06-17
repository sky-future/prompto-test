package com.prompto.model.campaign;


import com.prompto.model.campaignStatus.CampaignStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Campaign {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private Long templateId;

    @ElementCollection
    private List<Long > contactIds;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime scheduledAt;
}
