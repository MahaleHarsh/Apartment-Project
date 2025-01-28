package com.example.apartmentservice.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apartment {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String address;
    private int noiseLevel;
    private int floor;
}
