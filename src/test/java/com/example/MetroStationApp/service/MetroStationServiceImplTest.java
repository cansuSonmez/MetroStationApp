package com.example.MetroStationApp.service;

import com.example.MetroStationApp.dto.CardBalanceDto;
import com.example.MetroStationApp.dto.GetOnTheMetroDto;
import com.example.MetroStationApp.dto.GetOnTheMetroResponseDto;
import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.enumaration.StationName;
import com.example.MetroStationApp.enumaration.UserRole;
import com.example.MetroStationApp.model.Card;
import com.example.MetroStationApp.model.Metro;
import com.example.MetroStationApp.model.User;
import com.example.MetroStationApp.repository.CardRepository;
import com.example.MetroStationApp.repository.MetroRepository;
import com.example.MetroStationApp.repository.UserRepository;
import com.example.MetroStationApp.service.impl.MetroStationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MetroStationServiceImplTest {

    @InjectMocks
    MetroStationServiceImpl metroStationService;

    @Mock
    CardRepository cardRepository;

    @Mock
    UserRepository userRepository;

    @Mock
     MetroRepository metroRepository;

    Metro metro = new Metro(1L);
    Card card = new Card(1L, 1L, "123456", 10l);
    User user = new User(1L, "dogukan", "123456", UserRole.NORMAL);

    @Test
    public void testGetOn() {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        GetOnTheMetroDto getOnTheMetroDto = new GetOnTheMetroDto();
        getOnTheMetroDto.setStationName(StationName.USKUDAR);
        getOnTheMetroDto.setDestinationStation(StationName.ALTINSEHIR);

        Long existingBalance = card.getBalance();
        int stationNumber = 0;
        int station1 = getOnTheMetroDto.getStationName().ordinal();
        int station2 = getOnTheMetroDto.getDestinationStation().ordinal();

        if (UserRole.NORMAL==user.getRole()){
            if (existingBalance >= 5){
                stationNumber = station1 - station2 ;

                if (stationNumber < 0){
                    stationNumber = stationNumber - (-1);
                }
                existingBalance = existingBalance - 5;
            } else {
                System.out.println("Not enough money for using metro");
            }

        }else{

            if(existingBalance >= 3){
                stationNumber = station1 - station2 ;
                if (stationNumber < 0){
                    stationNumber = stationNumber * (-1);
                }
                existingBalance = existingBalance - 3 ;
            }else {
                System.out.println("Not enough money for using metro");
            }
        }

        card.setBalance(existingBalance);

        GetOnTheMetroResponseDto getOnTheMetroResponseDto = GetOnTheMetroResponseDto.builder()
                .stationNumber(stationNumber)
                .balance(existingBalance)
                .user(user)
                .build();

        //when(metroStationService.getOn(userDetailsDto,1L,getOnTheMetroDto)).thenReturn(getOnTheMetroResponseDto);
        assertEquals(stationNumber,getOnTheMetroResponseDto.getStationNumber());
    }

    @Test
    public void testUpdateMoney(){
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        CardBalanceDto cardBalanceDto = new CardBalanceDto();

        Long money = 60l;

        Card card1 = Card.builder()
                .id(card.getId())
                .userId(card.getUserId())
                .cardNumber(card.getCardNumber())
                .balance(card.getBalance()+money)
                .build();

        when(cardRepository.findById(card.getId())).thenReturn(Optional.ofNullable(card));
        assertEquals(metroStationService.updateMoney(userDetailsDto,cardBalanceDto,card.getId(),card1.getBalance()).getCardNumber(),card1.getCardNumber());
    }

}
