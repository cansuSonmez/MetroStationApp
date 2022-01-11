package com.example.MetroStationApp.service;

import com.example.MetroStationApp.dto.*;
import com.example.MetroStationApp.model.Card;

public interface MetroStationService {

    GetOnTheMetroResponseDto getOn(UserDetailsDto userDetailsDto,Long cardId, GetOnTheMetroDto getOnTheMetroDto);

    Card updateMoney(UserDetailsDto userDetailsDto, CardBalanceDto cardBalanceDto, Long cardId, Long money);
}
