package com.smartagri.backend.controller;

import com.smartagri.backend.model.MarketPrice;
import com.smartagri.backend.repository.MarketPriceRepository;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MarketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MarketPriceRepository repository;

    @InjectMocks
    private MarketController marketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marketController).build();
    }

    @Test
    public void testGetAllPrices() throws Exception {
        MarketPrice mp1 = new MarketPrice();
        mp1.setId(1L);
        mp1.setCrop("Tomato");
        MarketPrice mp2 = new MarketPrice();
        mp2.setId(2L);
        mp2.setCrop("Potato");

        when(repository.findAll()).thenReturn(Arrays.asList(mp1, mp2));

        mockMvc.perform(get("/api/market/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crop").value("Tomato"))
                .andExpect(jsonPath("$[1].crop").value("Potato"));

        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetPricesByCrop() throws Exception {
        MarketPrice mp1 = new MarketPrice();
        mp1.setId(1L);
        mp1.setCrop("Tomato");

        when(repository.findByCropIgnoreCase("Tomato")).thenReturn(Collections.singletonList(mp1));

        mockMvc.perform(get("/api/market/prices").param("crop", "Tomato"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crop").value("Tomato"));

        verify(repository, times(1)).findByCropIgnoreCase("Tomato");
    }
}
