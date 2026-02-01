package com.yapock.kynoapp.dal.repositories;

import com.yapock.kynoapp.dal.models.breed.Breed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BreedRepositoryTest {
    @Autowired
    BreedRepository breedRepository;

    @Test
    void testGetBreedListByName(){
        List<Breed> breeds = breedRepository.findAllByNameIsLikeIgnoreCase("%berger%");
        assertNotNull(breeds);
    }
}