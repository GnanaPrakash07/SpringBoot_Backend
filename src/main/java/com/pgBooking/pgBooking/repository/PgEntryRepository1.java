package com.pgBooking.pgBooking.repository;

import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.PgEntry1;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PgEntryRepository1 extends MongoRepository<PgEntry1, ObjectId> {

}
