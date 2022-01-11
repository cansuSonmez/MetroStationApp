package com.example.MetroStationApp.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CardDto {

    /*
    Card get endpointi için
     */

    @NotNull(message = "this field is not null")
    private Long userId;

    @NotEmpty(message = "this field is not null")
    @Size(min=4, max = 8)
    private String cardNumber;

    @NotNull(message = "this field is not null")
    private Long balance;


}
