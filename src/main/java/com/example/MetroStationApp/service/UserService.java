package com.example.MetroStationApp.service;

import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.dto.UserDto;
import com.example.MetroStationApp.model.User;

public interface UserService {

    User createUser(UserDto userDto);

    User getUser(UserDetailsDto userDetailsDto, Long userId);

    User updateUser(UserDetailsDto userDetailsDto, Long userId, UserDto userDto);

    User deleteUser(UserDetailsDto userDetailsDto, Long userId);
}
