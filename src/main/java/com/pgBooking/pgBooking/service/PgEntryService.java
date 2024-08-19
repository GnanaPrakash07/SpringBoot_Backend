package com.pgBooking.pgBooking.service;

import com.pgBooking.pgBooking.entry.BookingEntry;
import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.PgEntry1;
import com.pgBooking.pgBooking.repository.PgEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PgEntryService {
@Autowired
    private PgEntryRepository pgEntryRepository;
@Autowired
private  BookingService bookingService;
private static final Logger logger = LoggerFactory.getLogger(PgEntryService.class);
@Transactional
public void saveEntry(PgEntry pgEntry, ObjectId myId) throws EntityNotFoundException {
    try {
        Optional<BookingEntry> bookingEntryOpt = bookingService.findById(myId);

        if (bookingEntryOpt.isPresent()) {
            BookingEntry bookingEntry = bookingEntryOpt.get();
            if (pgEntry.getId() == null) {
                pgEntry = pgEntryRepository.save(pgEntry);
            }
            bookingEntry.getPgEntries().add(pgEntry);
            bookingService.save(bookingEntry);
        } else {

            throw new EntityNotFoundException("BookingEntry with ID " + myId + " not found.");
        }
    } catch (Exception e) {
        logger.info("error occured int he PG entry");
        System.out.println(e);
        throw new RuntimeException("An error occurred while saving the entry", e);
    }
}

    public void deleteById(ObjectId bookingEntryId, ObjectId pgEntryId) throws EntityNotFoundException {
        Optional<BookingEntry> bookingEntryOpt = bookingService.findById(bookingEntryId);
        if (bookingEntryOpt.isPresent()) {
            BookingEntry bookingEntry = bookingEntryOpt.get();
            PgEntry pgEntryToRemove = bookingEntry.getPgEntries()
                    .stream()
                    .filter(pgEntry -> pgEntry.getId().equals(pgEntryId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("PgEntry with ID " + pgEntryId + " not found in BookingEntry"));
            bookingEntry.getPgEntries().remove(pgEntryToRemove);
            bookingService.save(bookingEntry);
            pgEntryRepository.deleteById(pgEntryId);
        } else {
            throw new EntityNotFoundException("BookingEntry with ID " + bookingEntryId + " not found.");
        }
    }
    public List<PgEntry> getAll(){
        return pgEntryRepository.findAll();
    }
}
