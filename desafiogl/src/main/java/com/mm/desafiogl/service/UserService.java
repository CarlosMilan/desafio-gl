package com.mm.desafiogl.service;

import com.mm.desafiogl.domain.Role;
import com.mm.desafiogl.domain.User;
import com.mm.desafiogl.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<User> saveUser(UserDTO userDTO);
    Role saveRole(Role role);
    User getUser(String email);
    void addRoleToUser(String email, String roleName);
    List<User> getAllUsers();
}
