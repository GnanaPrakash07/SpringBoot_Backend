package com.pgBooking.pgBooking.controller;

import com.pgBooking.pgBooking.entry.BookingEntry;
import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.service.BookingService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
public class Booking {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @GetMapping("/all")
    public List<BookingEntry> getAllUsers(){
        return bookingService.getAll();
    }
    @GetMapping
    public ResponseEntity<?> getAllBookingEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user = userService.findByUserName(userName);
       List<BookingEntry> all=user.getBookingEntries();
       if(all!=null && !all.isEmpty()){
           return new ResponseEntity<>(all,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<BookingEntry>  createEntry(@RequestBody BookingEntry bookingEntry){
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
                bookingService.saveEntry(bookingEntry,userName);
            return new ResponseEntity<>(bookingEntry, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.OK);

        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<BookingEntry> getById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<BookingEntry> collect = user.getBookingEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<BookingEntry> bookingEntry = bookingService.findById(myId);

            if (bookingEntry.isPresent()) {
                return new ResponseEntity<>(bookingEntry.get(), HttpStatus.OK);
            }
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = bookingService.deleteById(myId, userName);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody BookingEntry newbookingEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<BookingEntry> collect = user.getBookingEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<BookingEntry> bookingEntry = bookingService.findById(id);

            if (bookingEntry.isPresent()) {
                BookingEntry old=bookingEntry.get();
                old.setName(newbookingEntry.getName()!=null && !newbookingEntry.getName().equals("") ? newbookingEntry.getName() : old.getName());
                old.setOwnerId(newbookingEntry.getOwnerId()!=null && !newbookingEntry.getOwnerId().equals("") ? newbookingEntry.getOwnerId() : old.getOwnerId());
                old.setUser(newbookingEntry.getUser()!=null && !newbookingEntry.getUser().equals("") ? newbookingEntry.getUser() : old.getUser());
                old.setBookingDate(newbookingEntry.getBookingDate()!=null && !newbookingEntry.getBookingDate().equals("") ? newbookingEntry.getBookingDate() : old.getBookingDate());
                bookingService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
