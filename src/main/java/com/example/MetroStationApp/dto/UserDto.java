package com.example.MetroStationApp.dto;

import com.example.MetroStationApp.enumaration.UserRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotEmpty
    @Size(min = 2, max = 64)
    public String username;

    @NotEmpty
    @Size(min = 6, max = 32)
    public String password;

    @NotNull
    public UserRole role;

}
