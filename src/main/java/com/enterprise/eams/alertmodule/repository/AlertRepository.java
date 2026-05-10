package com.enterprise.eams.alertmodule.repository;

import com.enterprise.eams.alertmodule.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByAsset_IdAndStatus(Long id, String active);
}