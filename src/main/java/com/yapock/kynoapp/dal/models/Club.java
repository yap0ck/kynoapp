package com.yapock.kynoapp.dal.models;

import com.yapock.kynoapp.dal.models.federation.Federation;
import jakarta.persistence.*;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String registrationNumber;
    private String iban;
    private String facebook;
    private String instagram;
    private String url;
    private String email;
    @ManyToOne
    @JoinColumn(name = "federation_id")
    private Federation federation;
}
