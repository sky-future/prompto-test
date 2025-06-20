package com.prompto.controller.campaign;

import com.prompto.dto.campaign.CampaignDTO;
import com.prompto.dto.campaignStatistics.CampaignStatisticsDTO;
import com.prompto.service.impl.CampaignServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
@Tag(name = "Campaign Management", description = "API to manage marketing campaigns")
public class CampaignController {
    private final CampaignServiceImpl campaignService;

    @Operation(summary = "Create a new campaign")
    @PostMapping
    public ResponseEntity<CampaignDTO> create(@RequestBody @Valid CampaignDTO dto) {
        CampaignDTO created = campaignService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/campaigns/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Get all campaigns")
    @GetMapping
    public ResponseEntity<List<CampaignDTO>> getAll() {
        return ResponseEntity.ok(campaignService.getAll());
    }

    @Operation(summary = "Get a campaign by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<CampaignDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getById(id));
    }

    @Operation(summary = "Update a campaign")
    @PutMapping("/{id}")
    public ResponseEntity<CampaignDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CampaignDTO dto) {
        return ResponseEntity.ok(campaignService.update(id, dto));
    }

    @Operation(summary = "Delete a campaign")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        campaignService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Send a campaign")
    @PostMapping("/{id}/send")
    public ResponseEntity<CampaignDTO> send(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.sendCampaign(id));
    }

    @Operation(summary = "Get campaign statistics")
    @GetMapping("/{id}/stats")
    public ResponseEntity<CampaignStatisticsDTO> stats(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaignStatisticsById(id));
    }
}