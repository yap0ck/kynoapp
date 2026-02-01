package com.yapock.kynoapp.bll;

import com.yapock.kynoapp.dal.models.BreedCSVRecord;

import java.io.File;
import java.util.List;

public interface BreedCsvService {
    List<BreedCSVRecord> convertCSV(File file);
}
