package com.pgBooking.pgBooking.entry;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "bookings_details")
@Data
@NoArgsConstructor
public class BookingEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String ownerId;
    private String name;
    private String user;
    private LocalDateTime bookingDate;
    @DBRef
    private List<PgEntry> pgEntries=new ArrayList<>();

}
