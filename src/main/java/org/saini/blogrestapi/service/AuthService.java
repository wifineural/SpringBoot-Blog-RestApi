package org.saini.blogrestapi.service;

import org.saini.blogrestapi.payload.LoginDto;
import org.saini.blogrestapi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

}
