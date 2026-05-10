package com.enterprise.eams.assetmodule.controller;

import com.enterprise.eams.assetmodule.dto.AssetRequestDTO;
import com.enterprise.eams.assetmodule.dto.AssetResponseDTO;
import com.enterprise.eams.assetmodule.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PreAuthorize("hasRole('MANAGER')")  //only Manager can create asset
    @PostMapping
    public ResponseEntity<AssetResponseDTO> createAsset(
           @Valid @RequestBody AssetRequestDTO dto) {

        return ResponseEntity.ok(assetService.createAsset(dto));
    }
    // Manager - can view all assets
   // Operator - can view only their assigned assets (handled in service layer)
    @PreAuthorize("hasAnyRole('MANAGER','OPERATOR')")
    @GetMapping
    public ResponseEntity<List<AssetResponseDTO>> getAllAssets(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String location) {

        return ResponseEntity.ok(assetService.getAllAssets(type, location));
    }

    //Get assets assigned to specific user
    @PreAuthorize("hasAnyRole('MANAGER','OPERATOR')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetResponseDTO>> getAssetsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(assetService.getAssetsByUser(userId));
    }

    @PreAuthorize("hasAnyRole('MANAGER','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> updateAsset(
            @PathVariable Long id,
           @Valid  @RequestBody AssetRequestDTO dto) {

        return ResponseEntity.ok(assetService.updateAsset(id, dto));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok("Asset deleted successfully");
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{assetId}/assign/{userId}")
    public ResponseEntity<AssetResponseDTO> assignAsset(
            @PathVariable Long assetId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(assetService.assignAsset(assetId, userId));
    }


}