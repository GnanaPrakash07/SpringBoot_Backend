package com.pgBooking.pgBooking.controller;

import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.PgEntry1;
import com.pgBooking.pgBooking.service.BookingService;
import com.pgBooking.pgBooking.service.EntityNotFoundException;
import com.pgBooking.pgBooking.service.PgEntryService;
import com.pgBooking.pgBooking.service.PgEntryService1;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/pg")
public class PgController {
    @Autowired
    private PgEntryService pgEntryService;
    @Autowired
    private PgEntryService1 pgEntryService1;
    @Autowired
    private BookingService bookingService;
    @GetMapping
    public List<PgEntry1> getAllUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return pgEntryService1.getAll();
    }
    @PostMapping("/{myId}")
    public ResponseEntity<PgEntry> createEntry(@RequestBody PgEntry pgEntry, @PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            pgEntryService.saveEntry(pgEntry,myId);
            return new ResponseEntity<>(pgEntry, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<PgEntry1> getById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<PgEntry1> pgEntry = pgEntryService1.findById(myId);
       if(pgEntry.isPresent()){
          return new ResponseEntity<>(pgEntry.get(), HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{id}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id,@PathVariable ObjectId myId) throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       pgEntryService.deleteById(id,myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
