package com.example.MetroStationApp.controller;

import com.example.MetroStationApp.dto.*;
import com.example.MetroStationApp.model.Card;
import com.example.MetroStationApp.service.MetroStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(
        value = "/metroStation",
        produces= MediaType.APPLICATION_JSON_VALUE,
        consumes=MediaType.APPLICATION_JSON_VALUE)
public class MetroStationController {

    private final MetroStationService metroStationService;

    public MetroStationController(MetroStationService metroStationService) {
        this.metroStationService = metroStationService;
    }

    @PostMapping("/{cardId}")
    private @ResponseBody
    GetOnTheMetroResponseDto getOn(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                   @RequestBody @Valid GetOnTheMetroDto getOnTheMetroDto,
                                   @PathVariable Long cardId) {
        return metroStationService.getOn(userDetailsDto,cardId,getOnTheMetroDto);
    }

    @PutMapping("/{cardId}/{money}")
    private @ResponseBody
    Card updateMoney(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                   @RequestBody @Valid CardBalanceDto cardBalanceDto,
                                   @PathVariable Long cardId,
                                    @PathVariable Long money) {
        return metroStationService.updateMoney(userDetailsDto,cardBalanceDto,cardId,money);
    }
}
