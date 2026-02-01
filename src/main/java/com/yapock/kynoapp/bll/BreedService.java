package com.yapock.kynoapp.bll;

import com.yapock.kynoapp.dal.models.breed.BreedDTO;

import java.util.List;

public interface BreedService {

    List<BreedDTO> listBreeds(String name, String group);
}
