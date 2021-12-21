package com.mm.desafiogl.controller;

import com.mm.desafiogl.domain.User;
import com.mm.desafiogl.dto.UserDTO;
import com.mm.desafiogl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> save(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<User>(this.userService.save(userDTO), HttpStatus.OK);
    }
}
