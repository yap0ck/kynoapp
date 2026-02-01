package com.yapock.kynoapp.dal.models.breed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;

@JsonDeserialize(builder = BreedDTO.BreedDTOBuilder.class)
@Builder
@Data
public class BreedDTO {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("breedGroup")
    private String breedGroup;
    @JsonProperty("section")
    private String section;
    @JsonProperty("url")
    private String url;
    @JsonProperty("image")
    private String image;
    @JsonProperty("pdf")
    private String pdf;
}
