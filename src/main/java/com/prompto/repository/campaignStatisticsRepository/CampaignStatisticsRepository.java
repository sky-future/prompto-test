package com.prompto.repository.campaignStatisticsRepository;

import com.prompto.model.campaignStatistics.CampaignStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignStatisticsRepository extends JpaRepository<CampaignStatistics, Long> {
}
