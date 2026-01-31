package com.yapock.kynoapp.bll;

import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.pl.federation.FederationDTO;
import com.yapock.kynoapp.pl.federation.FederationForm;

import java.util.List;

public interface FederationService {
    List<FederationDTO> findall();

    public FederationDTO findById(Long id);

    public void save(FederationForm federation);

    public void update(long id, FederationForm federation);
    public void delete(long id);
}
