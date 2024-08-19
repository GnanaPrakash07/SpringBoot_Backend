package com.pgBooking.pgBooking.controller;

import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.PgEntry1;
import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.service.EntityNotFoundException;
import com.pgBooking.pgBooking.service.PgEntryService;
import com.pgBooking.pgBooking.service.PgEntryService1;
import com.pgBooking.pgBooking.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
   @Autowired
   private PgEntryService pgEntryService;
    @Autowired
    private PgEntryService1 pgEntryService1;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/booked")
    public List<PgEntry> getAllBook(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return pgEntryService.getAll();
    }
    @PostMapping("/new-pg")
    public PgEntry1 createEntry(@RequestBody PgEntry1 pgEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return pgEntryService1.saveEntry(pgEntry);
    }
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.saveAdmin(user);
    }
    @PutMapping("id/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody PgEntry1 newpgEntry) throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PgEntry1 old=pgEntryService1.findById(id).orElse(null);
        if(old!=null){
            old.setOwnerId(newpgEntry.getOwnerId()!=null && !newpgEntry.getOwnerId().equals("") ? newpgEntry.getOwnerId() : old.getOwnerId());
            old.setName(newpgEntry.getName()!=null && !newpgEntry.getName().equals("") ? newpgEntry.getName() : old.getName());
            old.setPrice(newpgEntry.getPrice() != 0 ? newpgEntry.getPrice() : old.getPrice());
            old.setAddress(newpgEntry.getAddress()!=null && !newpgEntry.getAddress().equals("") ? newpgEntry.getAddress() : old.getAddress());
            old.setAmenities(newpgEntry.getAmenities()!=null && !newpgEntry.getAmenities().equals("") ? newpgEntry.getAmenities() : old.getAmenities());
            pgEntryService1.updateEntry(old, id);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("remove/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean removed = pgEntryService1.deleteById(myId);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
}
