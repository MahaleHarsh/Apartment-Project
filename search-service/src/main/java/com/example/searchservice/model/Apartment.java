package com.example.searchservice.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apartment {
    @Id
    private String id;
    private String name;
    private String address;
    private int noiseLevel;
    private int floor;
}
