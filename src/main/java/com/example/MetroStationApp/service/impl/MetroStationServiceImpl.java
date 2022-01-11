package com.example.MetroStationApp.service.impl;

import com.example.MetroStationApp.GlobalException.exception.EntityNotFoundException;
import com.example.MetroStationApp.config.FilterConfig;
import com.example.MetroStationApp.dto.*;
import com.example.MetroStationApp.enumaration.UserRole;
import com.example.MetroStationApp.model.Card;
import com.example.MetroStationApp.model.User;
import com.example.MetroStationApp.repository.CardRepository;
import com.example.MetroStationApp.repository.MetroRepository;
import com.example.MetroStationApp.repository.UserRepository;
import com.example.MetroStationApp.service.MetroStationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class MetroStationServiceImpl implements MetroStationService {

 //   private final MetroRepository metroRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public MetroStationServiceImpl(MetroRepository metroRepository, CardRepository cardRepository, UserRepository userRepository) {
       // this.metroRepository = metroRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public GetOnTheMetroResponseDto getOn(UserDetailsDto userDetailsDto, Long cardId, GetOnTheMetroDto getOnTheMetroDto) {
       Card cardExisting = this.cardRepository.findById(cardId)
               .orElseThrow (() -> new EntityNotFoundException("Entity not found"));

        User user = userRepository.findById(cardExisting.getUserId()).get();
        UserRole role = user.getRole();

        Long existingBalance = cardExisting.getBalance();
        int stationNumber = 0;


        if (role == UserRole.NORMAL) {

            if (existingBalance >= 5) {
                stationNumber = getOnTheMetroDto.getStationName().ordinal() - getOnTheMetroDto.getDestinationStation().ordinal();

                if (stationNumber < 0) {
                    stationNumber = stationNumber * (-1);
                }
                existingBalance = existingBalance - 5;
            } else {
                String message = "Not enough money for using metro";
                log.info(message);
            }

        } else {

            if (existingBalance >= 3) {
                stationNumber = getOnTheMetroDto.getStationName().ordinal() - getOnTheMetroDto.getDestinationStation().ordinal();

                if (stationNumber < 0) {
                    stationNumber = stationNumber * (-1);
                }
                existingBalance = existingBalance - 3;
            } else {
                String message = "Not enough money for using metro";
                log.info(message);
            }

        }

        cardExisting.setBalance(existingBalance);

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("Passenger oarded");

        return GetOnTheMetroResponseDto.builder()
                .stationNumber(stationNumber)
                .balance(existingBalance)
                .user(userDetailsDto.getUser())
                .build();

    }

    @Override
    @Transactional
    public Card updateMoney(UserDetailsDto userDetailsDto, CardBalanceDto cardBalanceDto, Long cardId, Long money) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        card.setBalance(card.getBalance() + money);
        cardRepository.save(card);

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User updated money", card);
        return card;
    }
}
