package com.yapock.kynoapp.dal.models;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Breed {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String breedGroup;
    private String section;
    private String country;
    private String url;
    private String image;
    private String pdf;
}
