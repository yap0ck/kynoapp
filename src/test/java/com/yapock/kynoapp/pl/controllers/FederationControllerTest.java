package com.yapock.kynoapp.pl.controllers;

import com.yapock.kynoapp.bll.FederationService;
import com.yapock.kynoapp.dal.models.federation.Federation;
import com.yapock.kynoapp.dal.models.federation.FederationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for validating the functionality of {@link FederationController}.
 * This class uses {@link WebMvcTest} to perform controller-specific tests
 * in isolation from other layers of the application.
 */
@WebMvcTest(FederationController.class)
class FederationControllerTest {

    private static final String BASE_PATH = "/federation";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FederationService federationService;

    private UUID federationId;
    private FederationDTO testFederationDTO;

    /**
     * Sets up the testing environment before each test.
     *
     * Initializes necessary test objects and assigns unique identifiers for use in testing.
     * Specifically, it generates a unique UUID for the federation and constructs a sample
     * FederationDTO instance with predefined attributes such as ID, name, country, and URL.
     */
    @BeforeEach
    void setUp() {
        federationId = UUID.randomUUID();

        testFederationDTO = FederationDTO.builder()
                .id(federationId)
                .name("Test Federation")
                .country("Test Country")
                .url("https://example.invalid")
                .build();
    }

    /**
     * Tests the HTTP GET endpoint for retrieving all federations.
     * Ensures that the response is a JSON array containing federation objects
     * with the expected attributes.
     *
     * Expected Behavior:
     * - The response status should be 200 OK.
     * - The response content type should be compatible with application/json.
     * - The response body should be an array of federation objects, where each
     *   object includes the following fields: id, name, country, and url.
     *
     * Test Setup:
     * - Mocks the federationService's findall method to return a predefined list
     *   of FederationDTO objects.
     *
     * Validations:
     * - Asserts that the returned JSON array matches the structure and content
     *   of the mocked federation data.
     *
     * Exceptions:
     * - This method throws an Exception in case of an unexpected error during
     *   the execution of the test case.
     */
    @Test
    void getAllFederations_returnsJsonArray() throws Exception {
        given(federationService.findall()).willReturn(List.of(testFederationDTO));

        mockMvc.perform(get(BASE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(federationId.toString()))
                .andExpect(jsonPath("$[0].name").value(testFederationDTO.getName()))
                .andExpect(jsonPath("$[0].country").value(testFederationDTO.getCountry()))
                .andExpect(jsonPath("$[0].url").value(testFederationDTO.getUrl()));
    }

    /**
     * Tests the endpoint for retrieving a specific federation by its ID.
     *
     * This test invokes a GET request to the endpoint for retrieving a federation
     * and expects a JSON representation of the federation to be returned. The test
     * verifies that the response status is 200 OK, the response content type is
     * application/json, and the JSON object in the response matches the expected
     * federation data.
     *
     * Preconditions:
     * - `federationService.findById` must return an Optional containing a valid FederationDTO
     *   when provided with the specified federation ID.
     * - The `testFederationDTO` object must be initialized and contain valid data
     *   for `id`, `name`, `country`, and `url` properties.
     *
     * Validations:
     * - The HTTP response status must be 200 OK.
     * - The content type of the HTTP response must be compatible with application/json.
     * - The JSON response must contain:
     *   - An `id` field matching the federation ID used in the request.
     *   - A `name` field matching the `testFederationDTO.getName()` value.
     *   - A `country` field matching the `testFederationDTO.getCountry()` value.
     *   - A `url` field matching the `testFederationDTO.getUrl()` value.
     *
     * @throws Exception if any unexpected error occurs during the test execution
     */
    @Test
    void getFederationById_returnsJsonObject() throws Exception {
        given(federationService.findById(federationId)).willReturn(Optional.ofNullable(testFederationDTO));

        mockMvc.perform(get(BASE_PATH + "/" + federationId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(federationId.toString()))
                .andExpect(jsonPath("$.name").value(testFederationDTO.getName()))
                .andExpect(jsonPath("$.country").value(testFederationDTO.getCountry()))
                .andExpect(jsonPath("$.url").value(testFederationDTO.getUrl()));
    }

    /**
     * Tests the scenario where a GET request is made to retrieve a federation by its ID,
     * but the federation is not found in the system.
     *
     * <b>Test Case:</b>
     * - Given: The federation service returns an empty Optional for the provided federation ID.
     * - When: A GET request is performed to fetch the federation by its ID.
     * - Then: The response status is 404 (Not Found).
     *
     * @throws Exception if an error occurs while executing the request.
     */
    @Test
    void getFederationById_returnsNotFound_whenFederationNotFound() throws Exception {
        given(federationService.findById(federationId)).willReturn(Optional.empty());

        mockMvc.perform(get(BASE_PATH + "/" + federationId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

        /**
         * Tests the functionality of creating a new federation via the controller.
         *
         * This method ensures the following:
         * 1. The create endpoint is invoked with the appropriate HTTP POST method
         *    and a JSON payload representing the federation details.
         * 2. The HTTP response status is 201 Created upon successful creation.
         * 3. The federationService's `create` method is called with a valid
         *    FederationDTO object.
         *
         * @throws Exception if the request execution or verification fails.
         */
        @Test
        void createFederation_returnsCreated_andCallsService() throws Exception {
            mockMvc.perform(post(BASE_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testFederationDTO)))
                    .andExpect(status().isCreated());

            verify(federationService).create(any(FederationDTO.class));
        }

        /**
         * Tests the creation of a Federation when the federation name is null.
         *
         * This method verifies that a federation creation request with a null name
         * results in a bad request (HTTP 400). It also ensures that the error
         * response contains exactly two error messages, which may correspond to
         * constraints such as the `@NotBlank` and `@NotNull` annotations on the
         * `name` field of the `FederationDTO`.
         *
         * Steps:
         * 1. Builds a `FederationDTO` object with a null `name` field while keeping
         *    other required fields valid.
         * 2. Sends a POST request to the Federation creation API with the
         *    constructed `FederationDTO` as JSON payload.
         * 3. Asserts that the response status is HTTP 400 (Bad Request).
         * 4. Further asserts that the JSON response body contains two error messages.
         *
         * @throws Exception if the mock request or processing fails during execution.
         */
        @Test
        void testCreateFederationNullFederationName() throws Exception{
            FederationDTO federationDTO = FederationDTO.builder()
                    .name(null)
                    .country("Belgique")
                    .url("http://example.valid").build();

            mockMvc.perform(post(BASE_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(federationDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.length()").value(2));
        }

    /**
     * Tests the scenario where a federation is attempted to be created with a null country value.
     *
     * Validates:
     * - The server returns a 400 Bad Request status.
     * - The error response contains two validation error messages.
     *
     * Preconditions:
     * - The {@link FederationDTO} object is built with the name set to "test name",
     *   the country set to null, and the URL set to a valid URL format.
     *
     * Execution:
     * - A POST request is made to the federation creation endpoint with the given {@link FederationDTO}.
     * - The response is validated to ensure proper handling of the null country value.
     *
     * Expected Behavior:
     * - The service rejects the request with a 400 Bad Request status due to a missing required field.
     * - The response contains validation error details indicating the reason for the failure.
     *
     * Throws:
     * - Exception if the request handling or assertions fail during test execution.
     */
    @Test
    void testCreateFederationNullCountry() throws Exception{
        FederationDTO federationDTO = FederationDTO.builder()
                .name("test name")
                .country(null)
                .url("http://example.valid").build();

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(federationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(2));
    }

        /**
         * Tests the `updateFederation` functionality by ensuring that it returns a "No Content" (204) status
         * when the update operation is successful and that the corresponding service method
         * is invoked with the correct ID and request body.
         *
         * This test performs the following:
         * 1. Configures the `federationService.update` mock to return a populated `Optional` of `Federation`
         *    when called with the correct ID and a `FederationDTO` instance.
         * 2. Executes a `PUT` request to the endpoint with the given ID and serialized `FederationDTO` as the request body.
         * 3. Validates that the response status is `204 No Content`.
         * 4. Verifies that the `update` method of the mocked `federationService` is invoked with the correct arguments.
         *
         * @throws Exception if the PUT request or validation encounters issues.
         */
        @Test
        void updateFederation_returnsNoContent_andCallsServiceWithIdAndBody() throws Exception {
            given(federationService.update(eq(federationId), any(FederationDTO.class)))
                    .willReturn(Optional.of(new Federation()));

            mockMvc.perform(put(BASE_PATH + "/" + federationId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testFederationDTO)))
                    .andExpect(status().isNoContent());

            verify(federationService).update(eq(federationId), any(FederationDTO.class));
        }

    /**
     * Tests the behavior of the update federation endpoint when the country field in
     * the FederationDTO is null.
     *
     * This test ensures that the server returns a 400 Bad Request status and includes
     * validation error details in the response when an update request is made with
     * null as the value for the mandatory country field.
     *
     * The test performs the following steps:
     * 1. Creates a FederationDTO object with a null value for the country field.
     * 2. Configures the mocked FederationService to return an Optional containing
     *    a Federation object when its update method is called.
     * 3. Performs a PUT request to the update endpoint with the JSON representation
     *    of the FederationDTO object.
     * 4. Validates the response status is 400 Bad Request.
     * 5. Verifies that the response JSON contains the expected number of validation errors.
     *
     * @throws Exception if an error occurs during the request or response processing.
     */
    @Test
    void testUpdateFederationNullCountry() throws Exception{
        FederationDTO federationDTO = FederationDTO.builder()
                .name("test name")
                .country(null)
                .url("http://example.valid").build();

        given(federationService.update(eq(federationId), any(FederationDTO.class)))
                .willReturn(Optional.of(new Federation()));

        mockMvc.perform(put(BASE_PATH + "/" + federationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(federationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(2));
    }

    /**
     * Tests the behavior of the `update` endpoint when attempting to update a federation
     * with a null name in the request payload.
     *
     * This test ensures that attempting to update a federation with a null name results
     * in a `400 Bad Request` HTTP response. It also verifies that the error response body
     * contains exactly two validation error messages, which indicate the reasons for the
     * request failure.
     *
     * Preconditions:
     * - A valid `federationId` value is provided.
     * - The `federationDTO` object is constructed with a null `name`, a valid `country`,
     *   and a valid `url`.
     *
     * Expected Outcomes:
     * - The `federationService.update` method is stubbed to return a non-empty `Optional`
     *   containing a `Federation` instance.
     * - The mock HTTP PUT request to the `update` endpoint returns a `400 Bad Request` status.
     * - The returned JSON response body contains two validation errors.
     *
     * Steps:
     * 1. Build a `FederationDTO` object with a null `name`.
     * 2. Stub the `federationService.update` method to simulate a successful update call.
     * 3. Perform a PUT request to the `update` API endpoint with the above `federationDTO`.
     * 4. Verify the response status is `400 Bad Request`.
     * 5. Assert that the validation error messages in the response body total two.
     */
    @Test
    void testUpdateFederationNullName() throws Exception{
        FederationDTO federationDTO = FederationDTO.builder()
                .name(null)
                .country("test country")
                .url("http://example.valid").build();

        given(federationService.update(eq(federationId), any(FederationDTO.class)))
                .willReturn(Optional.of(new Federation()));

        mockMvc.perform(put(BASE_PATH + "/" + federationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(federationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(2));
    }

        /**
         * Tests the deletion of a federation by its ID in the FederationController.
         *
         * This test verifies the following:
         * - The `delete` endpoint for a specified federation ID returns a status of 204 No Content upon successful deletion.
         * - The `federationService.delete(UUID id)` method is called exactly once with the correct federation ID.
         *
         * Preconditions:
         * - The `federationService.findById(UUID id)` method is stubbed to return an `Optional` containing
         *   a valid `FederationDTO` when invoked with the specified federation ID.
         *
         * Expectations:
         * - Upon executing the `DELETE` request to the specified endpoint, the response status is 204 No Content.
         * - The `federationService.delete(UUID id)` method is verified to have been called with the correct federation ID.
         *
         * Exceptions:
         * - Throws `Exception` if any unexpected error occurs during the execution of the test.
         */
    @Test
    void deleteFederation_returnsNoContent_andCallsServiceWithId() throws Exception {
        given(federationService.findById(federationId)).willReturn(Optional.of(testFederationDTO));

        mockMvc.perform(delete(BASE_PATH + "/" + federationId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(federationService).delete(eq(federationId));
    }
}