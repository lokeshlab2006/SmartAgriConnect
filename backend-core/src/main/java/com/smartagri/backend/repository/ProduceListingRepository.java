package com.smartagri.backend.repository;

import com.smartagri.backend.model.ProduceListing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduceListingRepository extends JpaRepository<ProduceListing, Long> {
    List<ProduceListing> findByCropIgnoreCaseContaining(String crop);
}
