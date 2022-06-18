package com.cognizant.user.cmd.api.security;

public interface PasswordEncoder {
    String hashPassword(String password);
}
