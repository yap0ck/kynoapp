package com.yapock.kynoapp.bll.serviceImpls;

import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.dal.repositories.FederationRepository;
import com.yapock.kynoapp.bll.FederationService;
import com.yapock.kynoapp.pl.federation.FederationDTO;
import com.yapock.kynoapp.pl.federation.FederationForm;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the FederationService interface. This service
 * facilitates operations related to federations such as retrieval, creation,
 * updating, and deletion by interacting with the data layer through
 * the FederationRepository.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FederationServiceImpl implements FederationService {
    /**
     * A repository interface for performing CRUD operations on
     * the {@link Federation} entity.
     * This is injected via constructor into the service class to facilitate
     * persistence and retrieval of data related to federations.
     */
    private final FederationRepository federationRepository;

    /**
     * Retrieves all federations from the database and converts them to a list of FederationDTO objects.
     *
     * @return a list of FederationDTO objects representing all federations.
     */
    @Override
    public List<FederationDTO> findall() {
        return federationRepository.findAll().stream().map(FederationDTO::fromEntity).toList();
    }

    /**
     * Retrieves a federation entity by its unique identifier (ID) and converts it into a FederationDTO object.
     *
     * @param id the unique identifier of the federation to retrieve
     * @return a FederationDTO representation of the federation entity, or null if no entity is found
     */
    @Override
    public FederationDTO findById(UUID id) {
        return FederationDTO.fromEntity(federationRepository.findById(id).orElse(null));
    }

    /**
     * Saves a new federation entity in the repository using the provided federation form data.
     *
     * @param federation the {@code FederationForm} containing the data to create a new federation
     */
    @Override
    public void save(FederationForm federation) {
        federationRepository.save(Federation.builder()
                .name(federation.name())
                .country(federation.country())
                .url(federation.url())
                .build());
    }

    /**
     * Updates an existing Federation entity with the details provided in the FederationForm.
     * If the Federation entity with the specified ID exists, its fields are updated and saved.
     *
     * @param id the unique identifier of the Federation entity to be updated
     * @param federation the FederationForm object containing the updated details for the Federation
     */
    @Override
    public void update(UUID id, FederationForm federation) {
        federationRepository.findById(id).ifPresent(federationEntity -> {
            federationEntity.setName(federation.name());
            federationEntity.setCountry(federation.country());
            federationEntity.setUrl(federation.url());
            federationRepository.save(federationEntity);
        });
    }

    /**
     * Deletes a federation record by its unique identifier.
     *
     * @param id the unique identifier of the federation to be deleted
     */
    @Override
    public void delete(UUID id) {
        federationRepository.deleteById(id);
    }


}
