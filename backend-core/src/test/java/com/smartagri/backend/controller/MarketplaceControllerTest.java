package com.smartagri.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartagri.backend.model.ProduceListing;
import com.smartagri.backend.repository.ProduceListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MarketplaceControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProduceListingRepository repository;

    @InjectMocks
    private MarketplaceController marketplaceController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marketplaceController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateListing() throws Exception {
        ProduceListing listing = new ProduceListing();
        listing.setFarmerName("John Doe");
        listing.setCrop("Wheat");
        listing.setQuantityKg(100.0);

        ProduceListing savedListing = new ProduceListing();
        savedListing.setId(1L);
        savedListing.setFarmerName("John Doe");
        savedListing.setCrop("Wheat");
        savedListing.setQuantityKg(100.0);

        when(repository.save(any(ProduceListing.class))).thenReturn(savedListing);

        mockMvc.perform(post("/api/marketplace/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.farmerName").value("John Doe"))
                .andExpect(jsonPath("$.crop").value("Wheat"));

        verify(repository, times(1)).save(any(ProduceListing.class));
    }

    @Test
    public void testGetListings() throws Exception {
        ProduceListing listing1 = new ProduceListing();
        listing1.setId(1L);
        listing1.setCrop("Wheat");

        when(repository.findAll()).thenReturn(Collections.singletonList(listing1));

        mockMvc.perform(get("/api/marketplace/listings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crop").value("Wheat"));

        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetListingsByCrop() throws Exception {
        ProduceListing listing1 = new ProduceListing();
        listing1.setId(1L);
        listing1.setCrop("Wheat");

        when(repository.findByCropIgnoreCaseContaining("Wheat")).thenReturn(Collections.singletonList(listing1));

        mockMvc.perform(get("/api/marketplace/listings").param("crop", "Wheat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crop").value("Wheat"));

        verify(repository, times(1)).findByCropIgnoreCaseContaining("Wheat");
    }

    @Test
    public void testDeleteListing() throws Exception {
        doNothing().when(repository).deleteById(1L);

        mockMvc.perform(delete("/api/marketplace/list/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(repository, times(1)).deleteById(1L);
    }
}
