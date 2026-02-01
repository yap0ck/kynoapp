package com.yapock.kynoapp.bll.serviceImpls;

import com.opencsv.bean.CsvToBeanBuilder;
import com.yapock.kynoapp.bll.BreedCsvService;
import com.yapock.kynoapp.dal.models.BreedCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BreedCsvServiceImpl implements BreedCsvService {
    @Override
    public List<BreedCSVRecord> convertCSV(File file) {

        try {
            List<BreedCSVRecord> breeds = new CsvToBeanBuilder<BreedCSVRecord>(new FileReader(file))
                    .withType(BreedCSVRecord.class)
                    .build().parse();
            return breeds;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
