package com.pgBooking.pgBooking.schedular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgBooking.pgBooking.cache.AppCache;
import com.pgBooking.pgBooking.entry.BookingEntry;
import com.pgBooking.pgBooking.entry.PgEntry;
import com.pgBooking.pgBooking.entry.User;
import com.pgBooking.pgBooking.repository.UserRepositoryImpl;
import com.pgBooking.pgBooking.service.EmailService;
import com.pgBooking.pgBooking.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
public class UserSchedular {
    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private UserRepositoryImpl userRepository;

    private static final String BOOKING_TOPIC = "Email-services";

    @Scheduled(cron = "0 0 10 * * MON") // Runs every Monday at 10 AM
    public void fetchUserAndSendSaMail() {
        List<User> users = userRepository.getUserSA();
        for (User user : users) {
            List<BookingEntry> bookingEntries = user.getBookingEntries();
            if (bookingEntries != null && !bookingEntries.isEmpty()) {
                StringBuilder emailContent = new StringBuilder();
                emailContent.append("Dear ").append(user.getUserName()).append(",\n\n");
                emailContent.append("Here are your booking details:\n");

                for (BookingEntry entry : bookingEntries) {
                    emailContent.append("Booking ID: ").append(entry.getId()).append("\n");
                    emailContent.append("Booking Date: ").append(entry.getBookingDate()).append("\n");

                    for (PgEntry pgEntry : entry.getPgEntries()) {
                        emailContent.append("PG Name: ").append(pgEntry.getName()).append("\n");
                    }

                    emailContent.append("\n");
                }

                emailContent.append("\nThank you for booking with us!");

                // Send the email
                emailService.sendEmail(user.getEmail(), "Your Booking Details", emailContent.toString());
            }
        }
    }

    // Optionally, you can include the method to clear the app cache if needed
    @Scheduled(cron = "0 0/10 * ? * *") // Example for every 10 minutes
    public void clearAppCache() {
        appCache.init();
    }
}

