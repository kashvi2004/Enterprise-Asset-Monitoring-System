package com.enterprise.eams.assetmodule.service;

import com.enterprise.eams.assetmodule.dto.AssetRequestDTO;
import com.enterprise.eams.assetmodule.dto.AssetResponseDTO;
import com.enterprise.eams.assetmodule.entity.Asset;
import com.enterprise.eams.assetmodule.repository.AssetRepository;
import com.enterprise.eams.common.email.UserEmailService;
import com.enterprise.eams.usermodule.entity.User;
import com.enterprise.eams.usermodule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepo;
    private final UserRepository userRepo;
    private final UserEmailService userEmailService;

    //create an asset
    public AssetResponseDTO createAsset(AssetRequestDTO dto) {

        User user = userRepo.findById(dto.getAssignedUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Asset asset = new Asset();
        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setLocation(dto.getLocation());
        asset.setThresholdTemp(dto.getThresholdTemp());
        asset.setThresholdPressure(dto.getThresholdPressure());
        asset.setAssignedTo(user);

        //default state
        asset.setStatus("NORMAL");

        Asset saved = assetRepo.save(asset);

        //notify user
        userEmailService.sendAssetAssignedEmail(
                user.getEmail(),
                user.getName(),
                saved
        );

        return mapToDTO(saved);
    }

    // get all (filter +role)
    public List<AssetResponseDTO> getAllAssets(String type, String location) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String role = user.getRole().name();

        // optional: normalize input
        if (type != null) type = type.toUpperCase();

        List<Asset> assets;

        // filtering
        if (type != null && location != null) {
            assets = assetRepo.findByTypeAndLocation(type, location);
        } else if (type != null) {
            assets = assetRepo.findByType(type);
        } else if (location != null) {
            assets = assetRepo.findByLocation(location);
        } else {
            assets = assetRepo.findAll();
        }

        // operator sees only own assets
        if (!"MANAGER".equals(role)) {
            assets = assets.stream()
                    .filter(a -> a.getAssignedTo() != null &&
                            a.getAssignedTo().getId().equals(user.getId()))
                    .toList();
        }

        return assets.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // get by id
    public AssetResponseDTO getAssetById(Long id) {
        Asset asset = assetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        return mapToDTO(asset);
    }

    // get by user
    public List<AssetResponseDTO> getAssetsByUser(Long userId) {
        return assetRepo.findByAssignedTo_Id(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // update asset details (except assignment)
    public AssetResponseDTO updateAsset(Long id, AssetRequestDTO dto) {

        Asset asset = assetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setLocation(dto.getLocation());
        asset.setThresholdTemp(dto.getThresholdTemp());
        asset.setThresholdPressure(dto.getThresholdPressure());

        Asset updated = assetRepo.save(asset);

        return mapToDTO(updated);
    }

    // delete an asset
    public void deleteAsset(Long id) {

        if (!assetRepo.existsById(id)) {
            throw new RuntimeException("Asset not found");
        }

        assetRepo.deleteById(id);
    }

    // assign / reassign
    public AssetResponseDTO assignAsset(Long assetId, Long userId) {

        Asset asset = assetRepo.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        User newUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User oldUser = asset.getAssignedTo();

        // first time assign
        if (oldUser == null) {

            asset.setAssignedTo(newUser);
            Asset saved = assetRepo.save(asset);

            userEmailService.sendAssetAssignedEmail(
                    newUser.getEmail(),
                    newUser.getName(),
                    saved
            );

            return mapToDTO(saved);
        }

        // same user
        if (oldUser.getId().equals(newUser.getId())) {
            throw new RuntimeException("Asset already assigned to this user");
        }

        // reassign
        asset.setAssignedTo(newUser);
        Asset saved = assetRepo.save(asset);

        // new user email
        userEmailService.sendAssetReassignedToNewUser(
                newUser.getEmail(),
                newUser.getName(),
                saved
        );

        // old user email
        userEmailService.sendReassignmentEmail(
                oldUser.getEmail(),
                oldUser.getName(),
                saved
        );

        return mapToDTO(saved);
    }

    //dto mapping
    private AssetResponseDTO mapToDTO(Asset asset) {

        return AssetResponseDTO.builder()
                .id(asset.getId())
                .name(asset.getName())
                .type(asset.getType())
                .location(asset.getLocation())
                .thresholdTemp(asset.getThresholdTemp())
                .thresholdPressure(asset.getThresholdPressure())
                .assignedUserName(
                        asset.getAssignedTo() != null
                                ? asset.getAssignedTo().getName()
                                : null
                )
                .status(asset.getStatus()) // important
                .build();
    }
}