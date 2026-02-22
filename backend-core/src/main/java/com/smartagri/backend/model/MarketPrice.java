package com.smartagri.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MarketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String market;
    private String crop;
    private Double currentPricePerKg;
    private String trend; // "UP", "DOWN", "STABLE"

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarket() { return market; }
    public void setMarket(String market) { this.market = market; }

    public String getCrop() { return crop; }
    public void setCrop(String crop) { this.crop = crop; }

    public Double getCurrentPricePerKg() { return currentPricePerKg; }
    public void setCurrentPricePerKg(Double currentPricePerKg) { this.currentPricePerKg = currentPricePerKg; }

    public String getTrend() { return trend; }
    public void setTrend(String trend) { this.trend = trend; }
}
