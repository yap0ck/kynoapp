package com.yapock.kynoapp.pl.federation;

import com.yapock.kynoapp.dal.models.Federation;

import java.util.UUID;

public record FederationDTO(
        UUID id,
        String name,
        String country,
        String url
) {
    public static FederationDTO fromEntity(Federation federation) {
        return new FederationDTO(
                federation.getId(),
                federation.getName(),
                federation.getCountry(),
                federation.getUrl()
        );
    }
}
