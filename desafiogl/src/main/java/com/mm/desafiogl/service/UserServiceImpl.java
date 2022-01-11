package com.mm.desafiogl.service;

import com.mm.desafiogl.configuration.Constants;
import com.mm.desafiogl.domain.Error;
import com.mm.desafiogl.domain.Role;
import com.mm.desafiogl.domain.User;
import com.mm.desafiogl.dto.UserDTO;
import com.mm.desafiogl.repository.RoleRepository;
import com.mm.desafiogl.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<User> saveUser(UserDTO userDTO) {

        List<Error> errors = new ArrayList<>();
        errors = checkPassword(userDTO.getPassword());
        User user = userDtoToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return new ResponseEntity<User>(this.userRepository.save(user), HttpStatus.CREATED);
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public User getUser(String email) {
        return null;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = this.userRepository.findByEmail(email);
        Role role = this.roleRepository.findByName(roleName);
        LOGGER.info("Adding role {} to user {}0", roleName, user.getName());
        user.getRoles().add(role);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    private User userDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setPhones(userDTO.getPhones());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        return user;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = evaluateRegex(email, Constants.EMAIL_REGEX);
        return matcher.find();
    }

    private List<Error> checkPassword(String password) {
        List<Error> errors = new ArrayList<>();

        if (!checkCapitalsAmount(password)) {
            Error error = new Error(new Timestamp(new Date().getTime()), 4002, "Password must have a capital letter");
            errors.add(error);
        }

        if (!checkNumbersAmount(password)) {
            Error error = new Error(new Timestamp(new Date().getTime()), 4003, "Password must have two numbers");
            errors.add(error);
        }

        if (password.length() > 12 || password.length() < 8) {
            Error error = new Error(new Timestamp(new Date().getTime()), 4004, "Password must be between 8 and 12 characters");
            errors.add(error);
        }

        return errors;
    }

    private boolean checkCapitalsAmount(String password) {
        Matcher matcher = evaluateRegex(password, Constants.CAPITAL_REGEX);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count == 1;
    }

    private boolean checkNumbersAmount(String password) {
        Matcher matcher = evaluateRegex(password, Constants.NUMBER_REGEX);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count == 2;
    }

    private Matcher evaluateRegex(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username);

        if ( user == null ) {
            LOGGER.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            LOGGER.info("User found in database: " + username);
        }

        Collection<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
