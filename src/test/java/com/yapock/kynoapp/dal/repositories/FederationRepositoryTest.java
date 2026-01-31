package com.yapock.kynoapp.dal.repositories;

import com.yapock.kynoapp.dal.models.Federation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Unit tests for the {@code FederationRepository} ensuring integration with the database.
 * This test class validates the functionality of CRUD (Create, Read, Update, Delete) 
 * operations and custom behavior of the {@code FederationRepository}.
 *
 * These tests use the {@code @DataJpaTest} annotation to set up an in-memory database 
 * and the necessary components for JPA operations, allowing effective verification 
 * of repository behavior.
 *
 * Test Methods:
 *
 * <ul>
 *     <li>{@code testSaveFederation}: Tests saving a {@code Federation} entity to the repository.</li>
 *     <li>{@code testFindAllFederations}: Validates retrieving all available {@code Federation} entities.</li>
 *     <li>{@code testGetFederationById}: Verifies fetching a specific {@code Federation} by its ID.</li>
 *     <li>{@code testUpdateFederation}: Ensures proper updating of an existing {@code Federation} entity.</li>
 *     <li>{@code testDeleteFederation}: Confirms successful deletion of a {@code Federation} entity by its ID.</li>
 * </ul>
 *
 * Annotations Used:
 *
 * <ul>
 *     <li>{@code @DataJpaTest}: Configures the test for JPA-related components with in-memory database support.</li>
 *     <li>{@code @Autowired}: Injects the {@code FederationRepository} bean into the test class.</li>
 *     <li>{@code @Test}: Marks a method as a test case.</li>
 * </ul>
 */
@DataJpaTest
class FederationRepositoryTest {

    private static final String TEST_FEDERATION_NAME = "Test Federation";
    private static final String TEST_COUNTRY = "Test Country";
    private static final String TEST_URL = "http://testfederation.com";

    private static final String FEDERATION_1_NAME = "Federation 1";
    private static final String FEDERATION_1_COUNTRY = "Country 1";
    private static final String FEDERATION_1_URL = "http://federation1.com";

    private static final String FEDERATION_2_NAME = "Federation 2";
    private static final String FEDERATION_2_COUNTRY = "Country 2";
    private static final String FEDERATION_2_URL = "http://federation2.com";

    private static final String ORIGINAL_NAME = "Original Name";
    private static final String ORIGINAL_COUNTRY = "Original Country";
    private static final String ORIGINAL_URL = "http://original.com";

    private static final String UPDATED_NAME = "Updated Name";
    private static final String UPDATED_COUNTRY = "Updated Country";
    private static final String UPDATED_URL = "http://updated.com";

    private static final String FEDERATION_TO_DELETE_NAME = "Federation To Delete";
    private static final String DELETE_URL = "http://delete.com";

    @Autowired
    private FederationRepository federationRepository;

    /**
     * Persists a new Federation entity in the repository with the specified attributes.
     *
     * This method constructs a Federation entity using the provided name, country, and URL,
     * and saves it to the repository. It returns the newly persisted Federation entity.
     *
     * @param name the name of the Federation to be persisted
     * @param country the country associated with the Federation
     * @param url the URL linked to the Federation
     * @return the persisted Federation entity
     */
    private Federation persistFederation(String name, String country, String url) {
        return federationRepository.save(Federation.builder()
                .name(name)
                .country(country)
                .url(url)
                .build());
    }

    /**
     * Tests the ability to save a Federation entity to the repository.
     *
     * This method verifies that a Federation entity can be persisted successfully
     * in the repository and that it is assigned a non-null, unique identifier upon saving.
     *
     * Test procedure:
     * 1. A Federation entity is created and saved to the repository using the
     *    `persistFederation` helper method, with a predefined name and default null values for other attributes.
     * 2. An assertion is performed to ensure that the saved Federation entity's ID is not null,
     *    indicating successful persistence and ID generation.
     */
    @Test
    void shouldSaveFederation() {
        Federation savedFederation = persistFederation(TEST_FEDERATION_NAME, null, null);

        assertThat(savedFederation.getId()).isNotNull();
    }

    /**
     * Tests the retrieval of all Federation entities from the repository.
     *
     * This method ensures that multiple Federation entities saved to the repository
     * can be successfully retrieved as part of a collection. The test validates that
     * the retrieved entities match the expected results, regardless of the order of retrieval.
     *
     * Test procedure:
     * 1. Two Federation entities are created and saved to the repository using the
     *    `persistFederation` helper method with predefined attributes: name, country, and URL.
     * 2. All Federation entities are retrieved from the repository using the `findAll` method.
     * 3. Assertions are performed to ensure:
     *    - The retrieved collection contains exactly the two persisted Federation entities.
     *    - The order of entities in the collection does not affect the test outcome.
     */
    @Test
    void shouldFindAllFederations() {
        Federation federation1 = persistFederation(FEDERATION_1_NAME, FEDERATION_1_COUNTRY, FEDERATION_1_URL);
        Federation federation2 = persistFederation(FEDERATION_2_NAME, FEDERATION_2_COUNTRY, FEDERATION_2_URL);

        var federations = federationRepository.findAll();

        assertThat(federations).containsExactlyInAnyOrder(federation1, federation2);
    }

    /**
     * Tests the retrieval of a Federation entity by its unique ID from the repository.
     *
     * This method ensures that a saved Federation entity can be accurately retrieved
     * using its ID. The test validates that all attributes of the retrieved entity
     * match the values of the original entity that was persisted.
     *
     * Test procedure:
     * 1. A Federation entity is created and saved to the repository using the
     *    `persistFederation` helper method with predefined attributes: name, country, and URL.
     * 2. The ID of the saved Federation entity is obtained.
     * 3. The `findById` method of the repository is called with the retrieved ID.
     * 4. Assertions are performed to:
     *    - Verify that the retrieved Federation entity is present in the repository.
     *    - Ensure that the ID, name, country, and URL of the retrieved entity
     *      match the respective values from the originally persisted Federation entity.
     */
    @Test
    void shouldGetFederationById() {
        Federation savedFederation = persistFederation(TEST_FEDERATION_NAME, TEST_COUNTRY, TEST_URL);
        var id = savedFederation.getId();

        var foundFederation = federationRepository.findById(id);

        assertThat(foundFederation)
                .get()
                .extracting(Federation::getId, Federation::getName, Federation::getCountry, Federation::getUrl)
                .containsExactly(id, TEST_FEDERATION_NAME, TEST_COUNTRY, TEST_URL);
    }

    /**
     * Tests the update functionality of a Federation entity within the repository.
     *
     * The method verifies that when an existing Federation entity is modified and saved,
     * the changes are successfully persisted in the repository.
     *
     * Test procedure:
     * 1. A Federation entity is initially saved using the `persistFederation` helper method with predefined values.
     * 2. The saved entity is updated with new attribute values for name, country, and URL.
     * 3. The updated entity is saved again to the repository.
     * 4. Assertions are performed to ensure:
     *    - The entity's ID remains unchanged.
     *    - The updated attribute values (name, country, and URL) match the new values provided.
     */
    @Test
    void shouldUpdateFederation() {
        Federation savedFederation = persistFederation(ORIGINAL_NAME, ORIGINAL_COUNTRY, ORIGINAL_URL);

        savedFederation.setName(UPDATED_NAME);
        savedFederation.setCountry(UPDATED_COUNTRY);
        savedFederation.setUrl(UPDATED_URL);

        Federation updatedFederation = federationRepository.save(savedFederation);

        assertThat(updatedFederation.getId()).isEqualTo(savedFederation.getId());
        assertThat(updatedFederation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(updatedFederation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(updatedFederation.getUrl()).isEqualTo(UPDATED_URL);
    }

    /**
     * Tests the deletion of a Federation entity from the repository.
     *
     * This method verifies that after persisting a Federation entity and subsequently
     * deleting it by its ID, the entity is no longer present in the repository.
     *
     * Steps:
     * 1. A Federation is saved to the repository using the `persistFederation` helper method.
     * 2. The saved Federation's ID is used to delete it from the repository.
     * 3. An assertion is performed to ensure the repository no longer contains the deleted entity.
     */
    @Test
    void shouldDeleteFederation() {
        Federation savedFederation = persistFederation(FEDERATION_TO_DELETE_NAME, TEST_COUNTRY, DELETE_URL);

        federationRepository.deleteById(savedFederation.getId());

        assertThat(federationRepository.findById(savedFederation.getId())).isEmpty();
    }
}