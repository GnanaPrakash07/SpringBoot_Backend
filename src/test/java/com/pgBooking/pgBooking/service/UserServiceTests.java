package com.pgBooking.pgBooking.service;

import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.repository.UserEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserEntryRepository userRepository;
    @Test
    @Disabled("Disabled until bug #123 is resolved")
    public void testFindUserName() {
        User user=userRepository.findByUserName("prakash");
      assertTrue(!user.getBookingEntries().isEmpty());
    }

}
