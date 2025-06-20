package com.prompto.service.impl;

import com.prompto.dto.campaign.CampaignDTO;
import com.prompto.dto.campaignStatistics.CampaignStatisticsDTO;
import com.prompto.exception.ResourceNotFoundException;
import com.prompto.model.campaign.Campaign;
import com.prompto.model.campaignStatistics.CampaignStatistics;
import com.prompto.model.campaignStatus.CampaignStatus;
import com.prompto.model.emailTemplate.EmailTemplate;
import com.prompto.repository.campaign.CampaignRepository;
import com.prompto.repository.campaignStatisticsRepository.CampaignStatisticsRepository;
import com.prompto.repository.emailTemplate.EmailTemplateRepository;
import com.prompto.service.CampaignService.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final EmailTemplateRepository emailTemplateRepository;
    private final CampaignStatisticsRepository campaignStatisticsRepository;

    @Override
    public List<CampaignDTO> getAll() {
        return campaignRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CampaignDTO getById(Long id) {
        Campaign c = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign " + id + " not found"));
        return toDTO(c);
    }

    @Override
    public CampaignDTO create(CampaignDTO dto) {
        EmailTemplate template = emailTemplateRepository.findById(dto.getTemplateId())
                .orElseThrow(() -> new ResourceNotFoundException("Template " + dto.getTemplateId() + " not found"));

        Campaign c = new Campaign();
        c.setName(dto.getName());
        c.setTemplate(template);
        c.setContactIds(dto.getContactIds());
        c.setStatus(CampaignStatus.DRAFT);
        LocalDateTime now = LocalDateTime.now();
        c.setCreatedAt(now);
        c.setScheduledAt(dto.getScheduledAt());

        return toDTO(campaignRepository.save(c));
    }

    @Override
    public CampaignDTO update(Long id, CampaignDTO dto) {
        Campaign c = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign " + id + " not found"));

        if (c.getStatus() == CampaignStatus.SEND) {
            throw new IllegalStateException("Cannot modify a SEND campaign");
        }

        EmailTemplate template = emailTemplateRepository.findById(dto.getTemplateId())
                .orElseThrow(() -> new ResourceNotFoundException("Template " + dto.getTemplateId() + " not found"));

        c.setName(dto.getName());
        c.setTemplate(template);
        c.setContactIds(dto.getContactIds());
        c.setScheduledAt(dto.getScheduledAt());
        return toDTO(campaignRepository.save(c));
    }

    @Override
    public void delete(Long id) {
        if (!campaignRepository.existsById(id)) {
            throw new ResourceNotFoundException("Campaign " + id + " not found");
        }
        campaignRepository.deleteById(id);
        campaignStatisticsRepository.deleteById(id);
    }

    @Override
    public CampaignDTO sendCampaign(Long id) {
        Campaign c = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign " + id + " not found"));
        if (c.getStatus() == CampaignStatus.SEND) return toDTO(c);

        // mock sending logic
        int total = c.getContactIds().size();
        CampaignStatistics stats = new CampaignStatistics();
        stats.setCampaignId(id);
        stats.setSent(total);
        stats.setDelivered(total);
        stats.setOpened(total / 2);   // 50% of contacts opened
        stats.setClicked(total / 3);  // 33% of contacts clicked
        campaignStatisticsRepository.save(stats);

        // set the campaign status to SENT
        c.setStatus(CampaignStatus.SEND);
        campaignRepository.save(c);

        return toDTO(c);
    }

    @Override
    public CampaignStatisticsDTO getCampaignStatisticsById(Long id) {
        CampaignStatistics s = campaignStatisticsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Statistics for campaign " + id + " not found"));
        return toDTO(s);
    }

    private CampaignDTO toDTO(Campaign c) {
        CampaignDTO dto = new CampaignDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setTemplateId(c.getTemplate().getId());
        dto.setContactIds(c.getContactIds());
        dto.setStatus(c.getStatus());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setScheduledAt(c.getScheduledAt());
        return dto;
    }

    private CampaignStatisticsDTO toDTO(CampaignStatistics s) {
        CampaignStatisticsDTO dto = new CampaignStatisticsDTO();
        dto.setCampaignId(s.getCampaignId());
        dto.setSent(s.getSent());
        dto.setDelivered(s.getDelivered());
        dto.setOpened(s.getOpened());
        dto.setClicked(s.getClicked());
        return dto;
    }
}