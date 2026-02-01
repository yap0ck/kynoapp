package com.yapock.kynoapp.dal.models.federation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank @NotNull
    @JsonProperty("name")
    private String name;
    @NotBlank @NotNull
    @JsonProperty("country")
    private String country;
    @JsonProperty("url")
    private String url;

}
