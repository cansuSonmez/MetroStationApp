package com.example.MetroStationApp.service.impl;

import com.example.MetroStationApp.GlobalException.exception.UsernameNotFoundException;
import com.example.MetroStationApp.config.FilterConfig;
import com.example.MetroStationApp.dto.UserDetailsDto;
import com.example.MetroStationApp.model.User;
import com.example.MetroStationApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("Load User by Username ", user);

        return new UserDetailsDto(user);
    }
}
