package com.pgBooking.pgBooking.repository;

import com.pgBooking.pgBooking.entry.ConfigJournalAppEntity;
import com.pgBooking.pgBooking.entry.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
