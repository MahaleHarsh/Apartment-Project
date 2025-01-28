package com.example.apartmentservice.controller;

import com.example.apartmentservice.model.Apartment;
import com.example.apartmentservice.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartment")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @PostMapping("/add")
    public Apartment addApartment(@RequestBody Apartment apartment) {
        return apartmentService.addApartment(apartment);
    }

    @DeleteMapping("/remove/{id}")
    public String removeApartment(@PathVariable String id) {
        apartmentService.removeApartment(id);
        return "Apartment removed successfully.";
    }

    @GetMapping("/list")
    public List<Apartment> listApartments() {
        return apartmentService.listApartments();
    }
}
