package com.example.MetroStationApp.service;

import com.example.MetroStationApp.dto.CardDto;
import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.model.Card;

public interface CardService {

    Card createCard (UserDetailsDto userDetailsDto, CardDto cardDto);

    Card getCard (Long cardId);

    Card deleteCard (UserDetailsDto userDetailsDto, Long cardId);

}
