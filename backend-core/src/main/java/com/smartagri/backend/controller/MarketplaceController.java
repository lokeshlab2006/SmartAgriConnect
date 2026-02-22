package com.smartagri.backend.controller;

import com.smartagri.backend.model.ProduceListing;
import com.smartagri.backend.repository.ProduceListingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/marketplace")
public class MarketplaceController {

    private final ProduceListingRepository repository;

    public MarketplaceController(ProduceListingRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/list")
    public ProduceListing createListing(@RequestBody ProduceListing listing) {
        return repository.save(listing);
    }

    @GetMapping("/listings")
    public List<ProduceListing> getListings(@RequestParam(required = false) String crop) {
        if (crop != null && !crop.isEmpty()) {
            return repository.findByCropIgnoreCaseContaining(crop);
        }
        return repository.findAll();
    }

    @DeleteMapping("/list/{id}")
    public Map<String, Boolean> deleteListing(@PathVariable Long id) {
        repository.deleteById(id);
        return Map.of("success", true);
    }
}
