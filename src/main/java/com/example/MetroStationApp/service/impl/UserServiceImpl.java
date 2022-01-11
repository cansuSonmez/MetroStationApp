package com.example.MetroStationApp.service.impl;

import com.example.MetroStationApp.config.FilterConfig;
import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.dto.UserDto;
import com.example.MetroStationApp.model.User;
import com.example.MetroStationApp.repository.UserRepository;
import com.example.MetroStationApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    /*
     * Create user can be called by anyone
     */
    @Override
    public User createUser(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("USer created", user);

        return this.userRepository.save(user);
    }

    @Override
    public User getUser(UserDetailsDto userDetailsDto, Long userId) {
        checkUserPermission(userDetailsDto, userId);

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User showed");

        return this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    @Transactional
    public User updateUser(UserDetailsDto userDetailsDto, Long userId, UserDto userDto) {
        checkUserPermission(userDetailsDto, userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        // user.setDeposit(userDto.getDeposit());
        user.setRole(userDto.getRole());

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User updated", user);

        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(UserDetailsDto userDetailsDto, Long userId) {
        checkUserPermission(userDetailsDto, userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        this.userRepository.delete(user);

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User deleted", user);

        return user;
    }

    private static void checkUserPermission(UserDetailsDto userDetailsDto, Long userId) {
        if (!userDetailsDto.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }
    }


}
