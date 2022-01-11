package com.example.MetroStationApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
public class Metro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String routeInformation = "Çekmekoy-Uskudar Metro ";

    private final double timeBetweenDestination = 2.5;
}
