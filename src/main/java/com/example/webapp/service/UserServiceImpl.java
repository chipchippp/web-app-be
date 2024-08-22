package com.example.webapp.service;

import com.example.webapp.components.JwtTokenUtil;
import com.example.webapp.dto.UserDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.models.Role;
import com.example.webapp.models.User;
import com.example.webapp.repository.RoleRepository;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User register(UserDTO userDTO) throws DataNotFound {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Phone number already exists");
        }
        User newUser = User.builder()
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
        newUser.setRole(role);
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()){
            throw new DataNotFound("Invalid phone and password");
        }
        User user = optionalUser.get();
        if (user.getFacebookAccountId() == 0 && user.getGoogleAccountId() == 0) {
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Wrong phone or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password, user.getAuthorities());
//        auth with java Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(optionalUser.get());
    }
}
