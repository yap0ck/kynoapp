package com.yapock.kynoapp.bll.serviceImpls;

import com.yapock.kynoapp.bll.BreedService;
import com.yapock.kynoapp.dal.mappers.BreedMapper;
import com.yapock.kynoapp.dal.models.breed.Breed;
import com.yapock.kynoapp.dal.models.breed.BreedDTO;
import com.yapock.kynoapp.dal.repositories.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreedServiceImpl implements BreedService {
    private final BreedRepository breedRepository;
    private final BreedMapper breedMapper;

    @Override
    public List<BreedDTO> listBreeds(String name, String group) {
        List<Breed> breeds;
        if(StringUtils.hasText(name) && !StringUtils.hasText(group)){
            breeds = listBreedByName(name);
        }else if(!StringUtils.hasText(name) && StringUtils.hasText(group)){
            breeds = listBreedByGroup(group);
        }else if(StringUtils.hasText(name) && StringUtils.hasText(group)){
            breeds = listBreedByNameAndByGroup(name, group);
        } else {
            breeds = breedRepository.findAll();
        }
        return breeds.stream()
                .map(breedMapper::breedToBreedDTO)
                .toList();
    }

    private List<Breed> listBreedByNameAndByGroup(String name, String group) {
        return breedRepository.findAllByNameIsLikeIgnoreCaseAndBreedGroupIsLikeIgnoreCase("%"+name+"%", "%"+group+"%");
    }

    private List<Breed> listBreedByGroup(String group) {
        return breedRepository.findAllByBreedGroupIsLikeIgnoreCase("%"+group+"%");
    }

    private List<Breed> listBreedByName(String name){
        return breedRepository.findAllByNameIsLikeIgnoreCase("%"+name+"%");
    }
}
