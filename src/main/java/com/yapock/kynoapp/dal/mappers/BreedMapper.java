package com.yapock.kynoapp.dal.mappers;

import com.yapock.kynoapp.dal.models.breed.Breed;
import com.yapock.kynoapp.dal.models.breed.BreedDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BreedMapper {
    Breed breedDTOtoBreed(BreedDTO breedDTO);
    BreedDTO breedToBreedDTO(Breed breed);
}
