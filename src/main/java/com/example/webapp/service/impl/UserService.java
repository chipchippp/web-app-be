package com.example.webapp.service.impl;


import com.example.webapp.dto.UserDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.models.User;

public interface UserService {
    User register(UserDTO userDTO) throws DataNotFound;
    String login(String phoneNumber, String password) throws Exception;
}
