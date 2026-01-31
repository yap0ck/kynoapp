package com.yapock.kynoapp.pl.federation;

import com.yapock.kynoapp.dal.models.Federation;

public record FederationDTO(
        long id,
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
