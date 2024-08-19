package com.pgBooking.pgBooking.repository;

import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<User, ObjectId> {
User findByUserName(String userName);
void deleteByUserName(String userName);
}
