package com.enterprise.eams.alertmodule.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AlertResponseDTO {

    private Long id;

    private Long assetId;
    private String assetName;

    private String type;
    private String message;

    private String status;

    private LocalDateTime triggeredAt;
}