package com.smartagri.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ProduceListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String farmerName;
    private String crop;
    private Double quantityKg;
    private Double expectedPricePerKg;
    private String location;
    private LocalDateTime listedAt;
    
    public ProduceListing() {
        this.listedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }

    public String getCrop() { return crop; }
    public void setCrop(String crop) { this.crop = crop; }

    public Double getQuantityKg() { return quantityKg; }
    public void setQuantityKg(Double quantityKg) { this.quantityKg = quantityKg; }

    public Double getExpectedPricePerKg() { return expectedPricePerKg; }
    public void setExpectedPricePerKg(Double expectedPricePerKg) { this.expectedPricePerKg = expectedPricePerKg; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getListedAt() { return listedAt; }
    public void setListedAt(LocalDateTime listedAt) { this.listedAt = listedAt; }
}
