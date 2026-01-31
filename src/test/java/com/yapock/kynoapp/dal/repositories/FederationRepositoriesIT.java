package com.yapock.kynoapp.dal.repositories;

import com.yapock.kynoapp.dal.models.Federation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class FederationRepositoriesIT {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    FederationRepository FederationRepository;

    @Test
    void testListFederations() {
        List<Federation> federations = FederationRepository.findAll();

        assertThat(federations).isNotNull();
        assertThat(federations).isNotEmpty();
        assertThat(federations.size()).isGreaterThan(0);
    }

    @Test
    void testCreateFederation() {
        Federation federation = Federation.builder()
                .name("Test Federation")
                .country("Test Country")
                .url("https://testfederation.com")
                .build();

        Federation savedFederation = FederationRepository.save(federation);

        assertThat(savedFederation).isNotNull();
        assertThat(savedFederation.getId()).isNotNull();
        assertThat(savedFederation.getName()).isEqualTo("Test Federation");
        assertThat(savedFederation.getCountry()).isEqualTo("Test Country");
        assertThat(savedFederation.getUrl()).isEqualTo("https://testfederation.com");
    }

    @Test
    void testFindFederationById() {
        Federation federation = Federation.builder()
                .name("Find By ID Federation")
                .country("Test Country")
                .url("https://findbyid.com")
                .build();

        Federation savedFederation = FederationRepository.save(federation);

        Federation foundFederation = FederationRepository.findById(savedFederation.getId()).orElse(null);

        assertThat(foundFederation).isNotNull();
        assertThat(foundFederation.getId()).isEqualTo(savedFederation.getId());
        assertThat(foundFederation.getName()).isEqualTo("Find By ID Federation");
    }

    @Test
    void testUpdateFederation() {
        Federation federation = Federation.builder()
                .name("Original Name")
                .country("Original Country")
                .url("https://original.com")
                .build();

        Federation savedFederation = FederationRepository.save(federation);

        savedFederation.setName("Updated Name");
        savedFederation.setCountry("Updated Country");
        savedFederation.setUrl("https://updated.com");

        Federation updatedFederation = FederationRepository.save(savedFederation);

        assertThat(updatedFederation).isNotNull();
        assertThat(updatedFederation.getId()).isEqualTo(savedFederation.getId());
        assertThat(updatedFederation.getName()).isEqualTo("Updated Name");
        assertThat(updatedFederation.getCountry()).isEqualTo("Updated Country");
        assertThat(updatedFederation.getUrl()).isEqualTo("https://updated.com");
    }

    @Test
    void testDeleteFederation() {
        Federation federation = Federation.builder()
                .name("Delete Test Federation")
                .country("Delete Country")
                .url("https://delete.com")
                .build();

        Federation savedFederation = FederationRepository.save(federation);

        FederationRepository.deleteById(savedFederation.getId());

        assertThat(FederationRepository.findById(savedFederation.getId())).isEmpty();
    }

    @Test
    void testFindFederationByCountry() {
        Federation federation1 = Federation.builder()
                .name("Federation 1")
                .country("France")
                .url("https://federation1.com")
                .build();

        Federation federation2 = Federation.builder()
                .name("Federation 2")
                .country("France")
                .url("https://federation2.com")
                .build();

        FederationRepository.save(federation1);
        FederationRepository.save(federation2);

        List<Federation> frenchFederations = FederationRepository.findAll().stream()
                .filter(f -> "France".equals(f.getCountry()))
                .toList();

        assertThat(frenchFederations).hasSizeGreaterThanOrEqualTo(2);
        assertThat(frenchFederations).allMatch(f -> "France".equals(f.getCountry()));
    }

    @Test
    void testFindFederationByName() {
        Federation federation = Federation.builder()
                .name("Unique Federation Name")
                .country("Test Country")
                .url("https://unique.com")
                .build();

        FederationRepository.save(federation);

        List<Federation> foundFederations = FederationRepository.findAll().stream()
                .filter(f -> "Unique Federation Name".equals(f.getName()))
                .toList();

        assertThat(foundFederations).hasSize(1);
        assertThat(foundFederations.get(0).getName()).isEqualTo("Unique Federation Name");
    }

}
