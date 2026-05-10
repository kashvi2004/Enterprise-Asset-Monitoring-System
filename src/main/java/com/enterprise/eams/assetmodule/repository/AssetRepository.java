package com.enterprise.eams.assetmodule.repository;

import com.enterprise.eams.assetmodule.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByAssignedTo_Id(Long userId);
    List<Asset> findByType(String type);

    List<Asset> findByLocation(String location);

    List<Asset> findByTypeAndLocation(String type, String location);
}