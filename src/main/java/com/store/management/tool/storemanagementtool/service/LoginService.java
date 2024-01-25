package com.store.management.tool.storemanagementtool.service;

import com.store.management.tool.storemanagementtool.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class LoginService {

    Map<String, String> userMap = new HashMap<>(Map.of("user", "password", "admin", "admin"));

    //Hard coded login setup for JWT showcase
    public Optional<Integer> login(UserDTO userDTO) {
        var password = userMap.get(userDTO.username());

        if(password != null && password.equals(userDTO.password())){
            return Optional.of(1);
        }

        return Optional.empty();
    }
}
