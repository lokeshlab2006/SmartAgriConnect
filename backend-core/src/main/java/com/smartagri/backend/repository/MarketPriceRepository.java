package com.smartagri.backend.repository;

import com.smartagri.backend.model.MarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {
    List<MarketPrice> findByCropIgnoreCase(String crop);
    List<MarketPrice> findByMarketIgnoreCase(String market);
}
