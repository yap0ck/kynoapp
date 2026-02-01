package com.yapock.kynoapp.dal.models;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreedCSVRecord {

    @CsvBindByName
    private Integer id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String group;
    @CsvBindByName
    private String section;
    @CsvBindByName
    private String provisional;
    @CsvBindByName
    private String country;
    @CsvBindByName
    private String url;
    @CsvBindByName
    private String image;
    @CsvBindByName
    private String pdf;
}
