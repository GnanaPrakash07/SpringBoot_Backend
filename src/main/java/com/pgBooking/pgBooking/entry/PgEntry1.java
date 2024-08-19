package com.pgBooking.pgBooking.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pg_entries")
@Data
@NoArgsConstructor
public class PgEntry1 {
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    private String address;
    private double price;
    private String amenities;
    private String ownerId;
}
