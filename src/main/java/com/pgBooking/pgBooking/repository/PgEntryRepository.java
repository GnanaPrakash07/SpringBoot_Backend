package com.pgBooking.pgBooking.repository;

import com.pgBooking.pgBooking.entry.PgEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PgEntryRepository extends MongoRepository<PgEntry, ObjectId> {

}
