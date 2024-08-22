package com.example.webapp.service;

import com.example.webapp.dto.UserDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.models.Role;
import com.example.webapp.models.User;
import com.example.webapp.repository.RoleRepository;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public User saveUser(UserDTO userDTO) throws DataNotFound {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Phone number already exists");
        }
        User newUser = User
                .builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFound("Role not found"));
        newUser.setRoleId(role);
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
//            String encodedPassword = passwordEncoder.encode(password);
//            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return "";
    }

//    @Override
//    public boolean existsByName(String name) {
//        return userRepository.existsByName(name);
//    }
}
