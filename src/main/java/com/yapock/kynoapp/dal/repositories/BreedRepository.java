package com.yapock.kynoapp.dal.repositories;

import com.yapock.kynoapp.dal.models.breed.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreedRepository extends JpaRepository<Breed, Integer> {
    List<Breed> findAllByNameIsLikeIgnoreCase(String name);
    List<Breed> findAllByBreedGroupIsLikeIgnoreCase(String group);
    List<Breed> findAllByNameIsLikeIgnoreCaseAndBreedGroupIsLikeIgnoreCase(String name, String group);
}
