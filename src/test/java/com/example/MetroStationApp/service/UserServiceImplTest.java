package com.example.MetroStationApp.service;

import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.dto.UserDto;
import com.example.MetroStationApp.enumaration.UserRole;
import com.example.MetroStationApp.model.User;
import com.example.MetroStationApp.repository.UserRepository;
import com.example.MetroStationApp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    User user = new User(1L, "cansu", "123456", UserRole.NORMAL);

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("cansu");
        userDto.setPassword(bCryptPasswordEncoder.encode("123456"));
        userDto.setRole(UserRole.NORMAL);

        User result = User.builder()
                .id(null)
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(UserRole.NORMAL)
                .build();
        when(userRepository.save(result)).thenReturn(user);
        assertEquals(userServiceImpl.createUser(userDto).getUsername(), result.getUsername());
    }

    @Test
    public void testGetUser() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertEquals(userServiceImpl.getUser(userDetailsDto, 1L), user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("hasan")
                .password("123456")
                .role(UserRole.NORMAL)
                .build();

        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        UserDto userDto = new UserDto();
        userDto.setUsername("cansu");
        userDto.setPassword("123456");
        userDto.setRole(UserRole.NORMAL);

        User userUpdated = User.builder()
                .id(1L)
                .username("cansu")
                .password(bCryptPasswordEncoder.encode("123456"))
                .role(UserRole.NORMAL)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(userUpdated)).thenReturn(userUpdated);
        assertEquals(userServiceImpl.updateUser(userDetailsDto,1L,userDto),userUpdated);
    }

    @Test
    public void testDeleteUser() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertEquals(userServiceImpl.deleteUser(userDetailsDto, 1L), user);
    }


}
