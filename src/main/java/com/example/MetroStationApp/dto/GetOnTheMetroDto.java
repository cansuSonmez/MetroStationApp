package com.example.MetroStationApp.dto;

import com.example.MetroStationApp.enumaration.StationName;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GetOnTheMetroDto {

    @NotNull
    @Enumerated(EnumType.STRING)
    private StationName stationName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StationName destinationStation;







}
