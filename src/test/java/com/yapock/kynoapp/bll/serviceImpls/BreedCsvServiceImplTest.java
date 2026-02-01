package com.yapock.kynoapp.bll.serviceImpls;

import com.yapock.kynoapp.bll.BreedCsvService;
import com.yapock.kynoapp.dal.models.breed.BreedCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BreedCsvServiceImplTest {
    BreedCsvService breedCsvService = new BreedCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/fci-breeds-fr.csv");
        List<BreedCSVRecord> breeds = breedCsvService.convertCSV(file);
        System.out.println(breeds.size());

        assertThat(breeds.size()).isGreaterThan(0);
    }

}