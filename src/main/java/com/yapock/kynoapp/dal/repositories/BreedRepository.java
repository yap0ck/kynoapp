package com.yapock.kynoapp.dal.repositories;

import com.yapock.kynoapp.dal.models.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Integer> {
}
