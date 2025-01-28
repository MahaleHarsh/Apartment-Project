package com.example.searchservice.repository;

import com.example.searchservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByApartmentId(String apartmentId);
}
