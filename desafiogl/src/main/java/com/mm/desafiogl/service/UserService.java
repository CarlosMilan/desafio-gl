package com.mm.desafiogl.service;

import com.mm.desafiogl.domain.User;
import com.mm.desafiogl.dto.UserDTO;
import com.mm.desafiogl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(UserDTO userDTO) {

        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setPhones(userDTO.getPhones());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());

        return this.userRepository.save(user);


    }
}
