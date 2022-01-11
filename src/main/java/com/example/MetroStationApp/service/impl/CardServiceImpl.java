package com.example.MetroStationApp.service.impl;

import com.example.MetroStationApp.GlobalException.exception.EntityNotFoundException;
import com.example.MetroStationApp.config.FilterConfig;
import com.example.MetroStationApp.dto.CardDto;
import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.model.Card;
import com.example.MetroStationApp.model.User;
import com.example.MetroStationApp.repository.CardRepository;
import com.example.MetroStationApp.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(UserDetailsDto userDetailsDto, CardDto cardDto) {
      Card card = Card.builder()
              .cardNumber(cardDto.getCardNumber())
              .balance(cardDto.getBalance())
              .userId(cardDto.getUserId())
              .build();

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("Card created", card);

      return this.cardRepository.save(card);
    }

    @Override
    public Card getCard(Long cardId) {

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("Card created");

    return this.cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

    }

    @Override
    @Transactional
    public Card deleteCard(UserDetailsDto userDetailsDto, Long cardId) {
        Card card = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        this.cardRepository.delete(card);

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("Card deleted", card);

        return card;
    }
}
