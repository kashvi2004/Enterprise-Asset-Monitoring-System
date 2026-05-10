package com.enterprise.eams.assetmodule.entity;

import com.enterprise.eams.usermodule.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String location;

    private double thresholdTemp;
    private double thresholdPressure;

    private String status; //normal , warning , critical
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
}