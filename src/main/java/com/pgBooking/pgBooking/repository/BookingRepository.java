package com.pgBooking.pgBooking.repository;

import com.pgBooking.pgBooking.entry.BookingEntry;
import com.pgBooking.pgBooking.entry.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<BookingEntry, ObjectId> {
}
