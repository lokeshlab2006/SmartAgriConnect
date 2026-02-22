package com.smartagri.backend.controller;

import com.smartagri.backend.model.MarketPrice;
import com.smartagri.backend.repository.MarketPriceRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketPriceRepository repository;

    public MarketController(MarketPriceRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            String[] crops = {"Tomato", "Potato", "Onion", "Wheat", "Rice"};
            String[] markets = {"Azadpur Mandi", "Vashi APMC", "Ghazipur"};
            Double[] basePrices = {15.0, 10.0, 20.0, 22.0, 30.0};

            for (int i = 0; i < crops.length; i++) {
                for (String market : markets) {
                    MarketPrice mp = new MarketPrice();
                    mp.setCrop(crops[i]);
                    mp.setMarket(market);
                    mp.setCurrentPricePerKg(basePrices[i] + (Math.random() * 5));
                    mp.setTrend(Math.random() > 0.5 ? "UP" : "DOWN");
                    repository.save(mp);
                }
            }
        }
    }

    @GetMapping("/prices")
    public List<MarketPrice> getAllPrices(@RequestParam(required = false) String crop) {
        if (crop != null && !crop.isEmpty()) {
            return repository.findByCropIgnoreCase(crop);
        }
        return repository.findAll();
    }
}
