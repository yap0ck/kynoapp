package com.yapock.kynoapp.bll;

import com.yapock.kynoapp.pl.federation.FederationDTO;
import com.yapock.kynoapp.pl.federation.FederationForm;

import java.util.List;
import java.util.UUID;

public interface FederationService {
    List<FederationDTO> findall();

    public FederationDTO findById(UUID id);

    public void save(FederationForm federation);

    public void update(UUID id, FederationForm federation);
    public void delete(UUID id);
}
