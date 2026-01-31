package com.yapock.kynoapp.dal.mappers;

import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.pl.federation.FederationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface FederationMappers {
    Federation federationDTOtoFederation(FederationDTO federationDTO);
    FederationDTO federationToFederationDTO(Federation federation);
}
