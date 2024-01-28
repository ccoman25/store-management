package com.store.management.tool.storemanagementtool.service;

import com.store.management.tool.storemanagementtool.dto.Role;
import com.store.management.tool.storemanagementtool.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    Map<String, UserDTO> userMap = new HashMap<>(Map.of("cristina", new UserDTO("cristina", "pass1", Role.USER.name()),
            "stelian", new UserDTO("stelian", "pass2", Role.ADMIN.name())));

    public Optional<String> login(UserDTO userDTO) {
        var user = userMap.get(userDTO.username());

        if(user.password() != null && user.password().equals(userDTO.password())){
            return Optional.of(user.role());
        }

        return Optional.empty();
    }
}
