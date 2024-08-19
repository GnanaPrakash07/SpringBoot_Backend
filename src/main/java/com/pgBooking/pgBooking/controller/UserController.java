package com.pgBooking.pgBooking.controller;

import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.repository.UserEntryRepository;
import com.pgBooking.pgBooking.service.PgEntryService;
import com.pgBooking.pgBooking.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
  private UserService userService;
@Autowired
private UserEntryRepository userEntryRepository;
@PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    String userName=authentication.getName();
    User userInDb= userService.findByUserName(userName);
     if(userInDb!=null){
         userInDb.setUserName(user.getUserName());
         userInDb.setPassword(user.getPassword());
         userService.saveNewUser((userInDb));
     }
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@DeleteMapping
    public ResponseEntity<?> deleteUserById(){
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
   userEntryRepository.deleteByUserName(authentication.getName());
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}
