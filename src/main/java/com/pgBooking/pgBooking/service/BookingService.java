package com.pgBooking.pgBooking.service;

import com.pgBooking.pgBooking.entry.BookingEntry;
import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;
    @Transactional
    public void saveEntry(BookingEntry bookingEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            bookingEntry.setBookingDate(LocalDateTime.now());
            BookingEntry saved = bookingRepository.save(bookingEntry);
            user.getBookingEntries().add(saved);
            userService.saveEntry(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry",e);
        }

    }
    public void saveEntry(BookingEntry bookingEntry){
        bookingRepository.save(bookingEntry);
    }
    public List<BookingEntry> getAll(){
        return bookingRepository.findAll();
    }
    public Optional<BookingEntry> findById(ObjectId id){
        return bookingRepository.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
           removed=user.getBookingEntries().removeIf(x -> x.getId().equals(id));
          if(removed) {
              userService.saveEntry(user);
              bookingRepository.deleteById(id);
          }
        }catch (Exception e){
            log.error("Error",e);
            throw new RuntimeException("An error occured while deletinng the entry.",e);
        }
        return removed;
    }

   public void save(BookingEntry bookingEntry) {
       bookingRepository.save(bookingEntry);
    }

}
