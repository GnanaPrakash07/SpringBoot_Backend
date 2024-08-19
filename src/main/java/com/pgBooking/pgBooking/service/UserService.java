package com.pgBooking.pgBooking.service;

import com.pgBooking.pgBooking.entry.BookingEntry;
import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.repository.BookingRepository;
import com.pgBooking.pgBooking.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserEntryRepository userRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveEntry(User user){
        userRepository.save(user);
    }
    public boolean saveNewUser(User user){
        try {


            String encodedPassword = passwordEncoder.encode(user.getPassword());
            System.out.println("Encoded Password: " + encodedPassword);
            user.setPassword(encodedPassword);
            user.setRoles(Arrays.asList("User"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            logger.info("Someone is trying to create an user account");
            logger.error("User name already exits");
            return false;
        }
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    public void saveAdmin(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("Encoded Password: " + encodedPassword);
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList("User","ADMIN"));
        userRepository.save(user);
    }
}
