package com.yapock.kynoapp.dal.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.Builder;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Federation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Setter
    private String name;
    @Setter
    private String country;
    @Setter
    private String url;


}
