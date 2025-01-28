package com.example.bookingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apartment {
    @Id
    private String id;  // UUID
    private String name;
    private String address;
    private int noiseLevel;
    private int floor;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
