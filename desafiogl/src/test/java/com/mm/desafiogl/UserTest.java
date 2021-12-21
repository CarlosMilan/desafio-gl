package com.mm.desafiogl;

import com.mm.desafiogl.domain.Phone;
import com.mm.desafiogl.domain.User;
import com.mm.desafiogl.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = com.mm.desafiogl.DesafioglApplication.class)
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUserTest() {

        Phone phone = new Phone();
        phone.setCitycode(264);
        phone.setNumber(155778899L);
        phone.setCountryCode("+54");

        List<Phone> phones = new ArrayList<>();
        phones.add(phone);

        User user = new User();
        user.setName("pepe");
        user.setEmail("pepe@");
        user.setPhones(phones);
        user.setPassword("1234");

        User userSaved = userRepository.save(user);
        UUID id = userSaved.getId();
        userRepository.findById(id);

        Assert.assertEquals("pepe", userSaved.getName());

    }

}
