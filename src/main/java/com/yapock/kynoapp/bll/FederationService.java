package com.yapock.kynoapp.bll;

import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.pl.federation.FederationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FederationService {
    List<FederationDTO> findall();

    Optional<FederationDTO> findById(UUID id);

    void create(FederationDTO federation);

    Optional<Federation> update(UUID id, FederationDTO federation);
    void delete(UUID id);
}
