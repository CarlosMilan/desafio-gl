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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test
    public void emailTest() {
        String email = "juan.perez_03@email.com.es";
        String regex = "\\b^[a-zA-Z0-9\\_\\.\\-]+\\b@\\b\\w+(\\.\\w+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        Assert.assertTrue("Bad formed email", matcher.find());
    }

    @Test
    public void capitalLetterTest() {
        String password = "1234T213asdnd";
        String capitalRegex = "[A-Z]{1}";
        Pattern pattern = Pattern.compile(capitalRegex);
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        Assert.assertEquals("There are too many capital letters", 1, count);
    }

    @Test
    public void numberAmountTest() {
        String password = "12Tasdnd";
        String capitalRegex = "[0-9]{1}";
        Pattern pattern = Pattern.compile(capitalRegex);
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        Assert.assertEquals("Password must have two numbers", 2, count);
    }

}
