package com.prompto.service.CampaignService;

import com.prompto.dto.campaign.CampaignDTO;
import com.prompto.dto.campaignStatistics.CampaignStatisticsDTO;

import java.util.List;

public interface CampaignService {

    List<CampaignDTO> getAll();
    CampaignDTO getById(Long id);
    CampaignDTO create(CampaignDTO dto);
    CampaignDTO update(Long id, CampaignDTO dto);
    void delete(Long id);
    CampaignDTO sendCampaign(Long id);
    CampaignStatisticsDTO getCampaignStatisticsById(Long id);
}
