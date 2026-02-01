package com.yapock.kynoapp.pl.controllers;

import com.yapock.kynoapp.dal.mappers.BreedMapper;
import com.yapock.kynoapp.dal.repositories.BreedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Rollback
class BreedControllerIT {

    private static final String BASE_PATH = "/breed";
    private static final String TEST_NAME = "berger";
    private static final String TEST_GROUP = "berger";
    private static final String TEST_SECTION = "test section";
    private static final String TEST_COUNTRY = "test country";
    private static final String TEST_URL = "test url";
    private static final String TEST_IMAGE = "test image";
    private static final String TEST_PDF = "test pdf";

    @Autowired
    BreedController breedController;

    @Autowired
    BreedRepository breedRepository;

    @Autowired
    BreedMapper breedMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testListBreedsByName_shouldReturn35Breeds() throws Exception {
        mockMvc.perform(get(BASE_PATH)
                .queryParam("name", TEST_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(35));
    }

    @Test
    void testListBreedsByGroup_shouldReturn47Breed() throws Exception {
        mockMvc.perform(get(BASE_PATH)
                .queryParam("group", TEST_GROUP))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(47));
    }

    @Test
    void testListBreedsByNameAndGroup_shouldReturn1Breed() throws Exception {
        mockMvc.perform(get(BASE_PATH)
                .queryParam("name", TEST_NAME)
                .queryParam("group", TEST_GROUP))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(25));
    }
}