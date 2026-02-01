package com.yapock.kynoapp.dal.utils;

import com.yapock.kynoapp.bll.BreedCsvService;
import com.yapock.kynoapp.dal.models.breed.Breed;
import com.yapock.kynoapp.dal.models.breed.BreedCSVRecord;
import com.yapock.kynoapp.dal.models.federation.Federation;
import com.yapock.kynoapp.dal.repositories.BreedRepository;
import com.yapock.kynoapp.dal.repositories.FederationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInit implements InitializingBean {
    private final FederationRepository federationRepository;
    private final BreedRepository breedRepository;
    private final BreedCsvService breedCsvService;

    @Value("${api.data-init}")
    private boolean insertion;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(insertion){
            loadFederations();
            loadCsvData();
        }
    }

    private void loadCsvData() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/fci-breeds-fr.csv");
        List<BreedCSVRecord> recs = breedCsvService.convertCSV(file);
        recs.forEach(rec ->
                breedRepository.save(Breed.builder()
                        .name(rec.getName())
                        .breedGroup(rec.getGroup())
                        .section(rec.getSection())
                        .country(rec.getCountry())
                        .url(rec.getUrl())
                        .image(rec.getImage())
                        .pdf(rec.getPdf())
                        .build()));

    }

    public void loadFederations(){
        Federation federation = Federation.builder()
                .name("Union Royale Cynologique Saint-Hubert")
                .country("Belgique")
                .url("https://www.kkush.be/")
                .build();
        federationRepository.save(federation);
    }
}
