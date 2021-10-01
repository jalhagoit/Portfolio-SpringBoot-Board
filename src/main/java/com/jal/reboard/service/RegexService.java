package com.jal.reboard.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegexService {

    public boolean usernamePattern(String username) {
        return Pattern.matches("^[a-z]{1}[a-z0-9]{3,19}$", username);
    }

    public boolean passwordPattern(String password) {
        return Pattern.matches("^[A-Za-z\\d$@$!%*?&]{4,}$", password);
    }
}
