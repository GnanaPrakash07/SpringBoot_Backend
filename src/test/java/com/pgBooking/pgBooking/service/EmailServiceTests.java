package com.pgBooking.pgBooking.service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.verify;
@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
       emailService.sendEmail("pranaybaddam3@gmail.com","mazza boi ki maa namaskaram","pora earipuka hari");

    }
}
