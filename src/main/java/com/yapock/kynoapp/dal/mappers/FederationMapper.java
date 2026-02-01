package com.yapock.kynoapp.dal.mappers;

import com.yapock.kynoapp.dal.models.federation.Federation;
import com.yapock.kynoapp.dal.models.federation.FederationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface FederationMapper {
    Federation federationDTOtoFederation(FederationDTO federationDTO);
    FederationDTO federationToFederationDTO(Federation federation);
}
