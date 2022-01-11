package com.mm.desafiogl.controller;

import com.mm.desafiogl.domain.Role;
import com.mm.desafiogl.domain.User;
import com.mm.desafiogl.dto.UserDTO;
import com.mm.desafiogl.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        return null;
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return null;
    }

    @PostMapping(value = "/sing-up", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> save(@RequestBody UserDTO userDTO) {
        return this.userService.saveUser(userDTO);
    }

    @PostMapping(value = "/role/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> save(@RequestBody Role Role) {
        return null;
    }

    @PostMapping("/add/role/{role}/to/{user}")
    public ResponseEntity<?> addRoleToUser(@PathVariable String role, @PathVariable(name = "user") String email) {
        return null;
    }

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody UserDTO userDTO) {
        return null;
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID uuid) {
        return null;
    }
}
