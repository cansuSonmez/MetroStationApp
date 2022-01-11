package com.example.MetroStationApp.dto;

import com.example.MetroStationApp.enumaration.StationName;
import com.example.MetroStationApp.model.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class GetOnTheMetroResponseDto {

    private int stationNumber;

    private User user;

    private Long balance;
}
