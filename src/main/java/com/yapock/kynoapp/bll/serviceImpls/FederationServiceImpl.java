package com.yapock.kynoapp.bll.serviceImpls;

import com.yapock.kynoapp.dal.mappers.FederationMappers;
import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.dal.repositories.FederationRepository;
import com.yapock.kynoapp.bll.FederationService;
import com.yapock.kynoapp.pl.federation.FederationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of the {@link FederationService} interface that provides the business logic
 * for managing federations. This service interacts with the persistence layer through the
 * {@link FederationRepository} to perform CRUD operations on {@link Federation} entities.
 *
 * The class uses constructor injection to initialize dependencies and is marked as a Spring
 * {@code @Service} to identify it as a service component in the application context.
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
    private final FederationMappers federationMappers;

    @Override
    public List<FederationDTO> findall() {
        return federationRepository.findAll()
                .stream()
                .map(federationMappers::federationToFederationDTO)
                .toList();
    }

    @Override
    public Optional<FederationDTO> findById(UUID id) {
        return federationRepository.findById(id)
                .map(federationMappers::federationToFederationDTO);
    }

    @Override
    public void create(FederationDTO federation) {
        federationRepository.save(federationMappers.federationDTOtoFederation(federation));
    }

    @Override
    public Optional<Federation> update(UUID id, FederationDTO federation) {
        Optional<Federation> existingOpt = federationRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }

        Federation existing = existingOpt.get();
        existing.setName(federation.getName());
        existing.setCountry(federation.getCountry());
        existing.setUrl(federation.getUrl());

        Federation saved = federationRepository.save(existing);
        return Optional.of(saved);
    }

    @Override
    public void delete(UUID id) {
        federationRepository.deleteById(id);
    }
}
