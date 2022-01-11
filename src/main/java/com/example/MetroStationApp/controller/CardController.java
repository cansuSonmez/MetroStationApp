package com.example.MetroStationApp.controller;

import com.example.MetroStationApp.dto.CardDto;
import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.model.Card;
import com.example.MetroStationApp.repository.CardRepository;
import com.example.MetroStationApp.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(
        value = "/cards",
        produces= MediaType.APPLICATION_JSON_VALUE,
        consumes=MediaType.APPLICATION_JSON_VALUE)
public class CardController {

    private final CardService cardService;

    public CardController( CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    private @ResponseBody
    Card createCard(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                    @RequestBody @Valid CardDto cardDto) {
        return cardService.createCard(userDetailsDto,cardDto);
    }

    @GetMapping("/{cardId}")
    private @ResponseBody
    Card getCard(@PathVariable Long cardId) {
        return cardService.getCard(cardId);
    }

    @DeleteMapping("/{cardId}")
    private @ResponseBody
    Card deleteCard(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                          @PathVariable Long cardId) {
        return cardService.deleteCard(userDetailsDto, cardId);
    }

}
