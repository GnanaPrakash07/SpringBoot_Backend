package com.pgBooking.pgBooking.service;

import com.pgBooking.pgBooking.entry.BookingEntry;
import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.PgEntry1;
import com.pgBooking.pgBooking.repository.PgEntryRepository;
import com.pgBooking.pgBooking.repository.PgEntryRepository1;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PgEntryService1 {
@Autowired
    private PgEntryRepository1 pgEntryRepository1;
    public List<PgEntry1> getAll(){
     return pgEntryRepository1.findAll();
 }
 public Optional<PgEntry1> findById(ObjectId id){
     return pgEntryRepository1.findById(id);
 }


    public PgEntry1 saveEntry(PgEntry1 pgEntry) {
        pgEntryRepository1.save(pgEntry);
        return pgEntry;
    }
    public PgEntry1 updateEntry(PgEntry1 newPgEntry, ObjectId id) throws EntityNotFoundException {
            Optional<PgEntry1> existingPgEntryOpt = pgEntryRepository1.findById(id);

            if (existingPgEntryOpt.isPresent()) {
                return pgEntryRepository1.save(newPgEntry);
            } else {
                throw new EntityNotFoundException("PgEntry with ID " + id + " not found.");
            }

    }
    public boolean deleteById(ObjectId myId) {
        Optional<PgEntry1> pgEntryOpt = pgEntryRepository1.findById(myId);

        if (pgEntryOpt.isPresent()) {
            pgEntryRepository1.deleteById(myId);
            return true;
        } else {
            return false;
        }
    }
}
