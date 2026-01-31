package com.yapock.kynoapp.pl.Controllers;

import com.yapock.kynoapp.dal.mappers.FederationMappers;
import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.dal.repositories.FederationRepository;
import com.yapock.kynoapp.pl.federation.FederationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the deletion functionality of the `deleteFederation` method in the `federationController`.
 *
 * This test verifies the following:
 * 1. An existing federation can be deleted by its unique ID.
 * 2. The response status code is HTTP 204 (NO_CONTENT).
 * 3. The federation is successfully removed from the repository, ensuring
 *    that subsequent retrieval attempts return no results.
 *
 * The test performs the following steps:
 * 1. Retrieves an existing federation via `firstPersistedFederation()`.
 * 2. Invokes the `deleteFederation` method with the federation's ID.
 * 3. Asserts that the response status code is HTTP 204 (NO_CONTENT).
 * 4. Confirms that the federation repository no longer contains the deleted entity.
 *
 * This test ensures that federation entities are removed successfully and their
 * associated resources are properly managed.
 */
@SpringBootTest
@Transactional
@Rollback
class FederationControllerIT {

    private static final String TEST_NAME = "test";
    private static final String TEST_COUNTRY = "test";
    private static final String TEST_URL = "test";
    private static final String UPDATED_NAME = "UPDATED";

    @Autowired
    FederationController federationController;

    @Autowired
    FederationRepository federationRepository;

    @Autowired
    FederationMappers federationMappers;

    /**
     * Tests the retrieval of a federation by its unique identifier.
     *
     * This method verifies that a federation can be successfully retrieved
     * from the system using its ID and that the resulting HTTP response
     * contains the expected status and body content.
     *
     * The test performs the following validations:
     * 1. Ensures the HTTP status code of the response is 200 (OK).
     * 2. Validates that the response body is not null and contains
     *    the expected federation details, specifically the ID and name.
     *
     * Dependencies:
     * - Uses the {@code firstPersistedFederation} method to retrieve
     *   a Federation entity from the database for testing purposes.
     * - Calls the {@code getFederation} method of the federationController
     *   to perform the retrieval.
     *
     * Preconditions:
     * - There must be at least one persisted federation in the database.
     *
     * Assertions:
     * - The response HTTP status code equals {@code HttpStatus.OK}.
     * - The response body is not null and contains the correct ID and
     *   name of the retrieved federation.
     */
    @Test
    void getById_returnsFederation() {
        Federation federation = firstPersistedFederation();

        ResponseEntity<FederationDTO> response = federationController.getFederation(federation.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .extracting(FederationDTO::getId, FederationDTO::getName)
                .containsExactly(federation.getId(), federation.getName());
    }

    /**
     * Tests the behavior of the `getFederation` method in the `federationController` when attempting to retrieve
     * a federation entity that does not exist. Ensures that the method throws a {@code NotFoundException}.
     *
     * <ul>
     *     <li>Generates a random UUID representing the ID of a non-existent federation entry.</li>
     *     <li>Verifies that a {@code NotFoundException} is thrown when the method is invoked with the missing ID.</li>
     * </ul>
     *
     * This test confirms that the controller handles missing resources appropriately by returning the expected
     * exception, which translates to an HTTP 404 status when exposed via the REST API.
     */
    @Test
    void getById_throwsNotFound_whenMissing() {
        assertThrows(NotFoundException.class, () -> federationController.getFederation(UUID.randomUUID()));
    }

    /**
     * Verifies that the `listFederations` method of the `federationController` returns a non-empty list of federations.
     *
     * This test ensures the following:
     * - The HTTP response status is 200 (OK).
     * - The response body contains a non-null and non-empty list.
     *
     * The method under test is expected to retrieve a list of federations from the system,
     * ensuring that there are stored federations in the repository or database.
     */
    @Test
    void listFederations_returnsNonEmptyList() {
        ResponseEntity<List<FederationDTO>> response = federationController.listFederations();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();
    }

    /**
     * Tests the behavior of the {@code listFederations} method when the federation repository is empty.
     *
     * The test ensures that:
     * 1. The repository is cleared of all federations using {@code federationRepository.deleteAll()}.
     * 2. When the {@code listFederations} method is called, it returns a response with:
     *    - An HTTP status code of 200 (OK).
     *    - An empty body, verifying that there are no federations present.
     *
     * This test verifies that the service handles an empty repository scenario correctly and returns
     * the expected response structure.
     */
    @Test
    void listFederations_returnsEmptyList_whenRepositoryIsEmpty() {
        federationRepository.deleteAll();

        ResponseEntity<List<FederationDTO>> response = federationController.listFederations();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    /**
     * Tests that the saveFederation method of the FederationController
     * successfully saves a federation and returns an HTTP status of CREATED.
     *
     * The test constructs a FederationDTO object with predefined test values
     * for name, country, and URL. It then invokes the saveFederation method
     * of the federationController with the DTO and verifies that the response
     * has a CREATED status code.
     *
     * Preconditions:
     * - A FederationDTO object is properly built using test constants for name,
     *   country, and URL.
     * - The federationController is available and properly configured.
     *
     * Expected Behavior:
     * - The response from the saveFederation method will have an HTTP status
     *   of CREATED (201).
     */
    @Test
    void saveFederation_returnsCreated() {
        FederationDTO dto = FederationDTO.builder()
                .name(TEST_NAME)
                .country(TEST_COUNTRY)
                .url(TEST_URL)
                .build();

        ResponseEntity<Void> response = federationController.saveFederation(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /**
     * Tests that the federation's name is successfully updated and the response returns HTTP 204 (NO_CONTENT).
     *
     * This test verifies that the `updateFederation` method in the FederationController updates the name of an
     * existing federation when valid data is provided. It also confirms that the method returns the expected
     * no-content response status.
     *
     * The test performs the following steps:
     * 1. Retrieves an existing federation using the helper method `firstPersistedFederation()`.
     * 2. Maps the retrieved federation to a DTO representation using `federationMappers.federationToFederationDTO`.
     * 3. Updates the name field of the DTO with a new value (`UPDATED_NAME`).
     * 4. Invokes the `updateFederation` method of the controller with the federation's ID and the updated DTO.
     * 5. Asserts that the response status code is HTTP 204 (NO_CONTENT).
     * 6. Retrieves the updated federation from the repository using its ID.
     * 7. Asserts that the name of the retrieved federation matches the updated name.
     */
    @Test
    void updateFederation_updatesName_andReturnsNoContent() {
        Federation federation = firstPersistedFederation();
        FederationDTO dto = federationMappers.federationToFederationDTO(federation);
        dto.setName(UPDATED_NAME);

        ResponseEntity<Void> response = federationController.updateFederation(federation.getId(), dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Federation updated = federationRepository.findById(federation.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo(UPDATED_NAME);
    }

    /**
     * Tests the behavior of the `updateFederation` method in the `federationController` class
     * when attempting to update a federation that does not exist.
     *
     * This test verifies that the method throws a `NotFoundException` when attempting to update
     * a federation identified by a randomly generated UUID that does not correspond to any
     * existing federation in the system.
     *
     * Steps:
     * 1. Creates a `FederationDTO` object with sample data using its builder.
     * 2. Calls the `updateFederation` method with a randomly generated UUID and the created
     *    `FederationDTO` object.
     * 3. Asserts that a `NotFoundException` is thrown, ensuring proper error handling for
     *    missing resources.
     *
     * Expected Behavior:
     * - The `federationController` should throw a `NotFoundException` with a 404 HTTP status
     *   when the specified federation ID does not exist in the system.
     */
    @Test
    void updateFederation_throwsNotFound_whenMissing() {
        FederationDTO dto = FederationDTO.builder()
                .name(TEST_NAME)
                .country(TEST_COUNTRY)
                .url(TEST_URL)
                .build();

        assertThrows(NotFoundException.class, () -> federationController.updateFederation(UUID.randomUUID(), dto));
    }

    /**
     * Validates that the `deleteFederation` method in `federationController` throws
     * a `NotFoundException` when attempting to delete a federation that does not exist.
     *
     * This test ensures that the appropriate exception is raised if a federation
     * with the specified UUID is not found in the database during the delete operation.
     *
     * The federation UUID used in this test is randomly generated and does not
     * correspond to any persisted federation.
     *
     * Expected Behavior:
     * - The `deleteFederation` method should throw a `NotFoundException`.
     *
     * Test Type: Unit Test
     */
    @Test
    void deleteFederation_throwsNotFound_whenMissing() {
        assertThrows(NotFoundException.class, () -> federationController.deleteFederation(UUID.randomUUID()));
    }

    /**
     * Tests the functionality of deleting a federation entity and verifying
     * that the entity is successfully removed from the repository.
     *
     * The test performs the following steps:
     * - Retrieves a persisted federation from the repository.
     * - Invokes the delete operation on the federation controller with the federation's ID.
     * - Asserts that the HTTP response status code is 204 (NO_CONTENT) indicating
     *   successful deletion.
     * - Confirms that the federation is no longer present in the repository
     *   using a lookup by ID.
     */
    @Test
    void deleteFederation_deletesEntity_andReturnsNoContent() {
        Federation federation = firstPersistedFederation();

        ResponseEntity<Void> response = federationController.deleteFederation(federation.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(federationRepository.findById(federation.getId())).isEmpty();
    }

    /**
     * Retrieves the first persisted Federation entity from the database.
     * Ensures that the database contains at least one Federation, and asserts this condition.
     *
     * @return the first Federation entity found in the database
     * @throws AssertionError if no Federation entities are found in the database
     */
    private Federation firstPersistedFederation() {
        List<Federation> federations = federationRepository.findAll();
        assertThat(federations)
                .as("Expected at least one Federation in database for this test")
                .isNotEmpty();
        return federations.getFirst();
    }
}