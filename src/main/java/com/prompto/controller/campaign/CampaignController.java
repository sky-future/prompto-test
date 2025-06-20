package com.prompto.controller.campaign;

import com.prompto.dto.campaign.CampaignDTO;
import com.prompto.dto.campaignStatistics.CampaignStatisticsDTO;
import com.prompto.service.impl.CampaignServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    @Autowired
    private final CampaignServiceImpl svc;


    @PostMapping
    public ResponseEntity<CampaignDTO> create(@RequestBody @Valid CampaignDTO dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @GetMapping
    public List<CampaignDTO> getAll() { return svc.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignDTO> update(@PathVariable Long id,
                                                   @RequestBody @Valid CampaignDTO dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<CampaignDTO> send(@PathVariable Long id) {
        return ResponseEntity.ok(svc.sendCampaign(id));
    }

    @GetMapping("/{id}/stats")
    public CampaignStatisticsDTO stats(@PathVariable Long id) {
        return svc.getCampaignStatisticsById(id);
    }

}
