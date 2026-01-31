package com.yapock.kynoapp.pl.federation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yapock.kynoapp.dal.models.Federation;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;
@JsonDeserialize(builder = FederationDTO.FederationDTOBuilder.class)
@Builder
@Data
public class FederationDTO {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("country")
    private String country;
    @JsonProperty("url")
    private String url;

}
