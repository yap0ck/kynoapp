package com.yapock.kynoapp.pl.Controllers;

import com.yapock.kynoapp.bll.FederationService;
import com.yapock.kynoapp.pl.federation.FederationDTO;
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
     * Initializes the test environment before each test method is executed.
     *
     * This method performs the following operations:
     * 1. Generates a new random UUID and assigns it to the `federationId` field.
     * 2. Creates an instance of `FederationDTO` with predefined test data, using the generated `federationId`.
     * 3. Creates an instance of `FederationForm` with matching test data.
     *
     * These initialized objects (`federationId`, `testFederationDTO`, and `testFederationForm`) are used
     * across various test cases to ensure consistent and isolated testing of the system under test.
     *
     * The method is annotated with `@BeforeEach`, ensuring that it is invoked once before the execution of each test method.
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
     * Tests the `GET /federation` endpoint to ensure it returns a JSON array of federations.
     *
     * This test verifies that the controller:
     * 1. Interacts with the `federationService` to fetch all federations.
     * 2. Returns an HTTP status of 200 (OK).
     * 3. Produces a response with a content type compatible with `application/json`.
     * 4. Includes a JSON structure that matches the expected list of federations.
     *
     * Assertions:
     * - The response body is verified as a JSON array.
     * - The first object in the array contains valid values for `id`, `name`, `country`, and `url` fields.
     *
     * Mocks:
     * - `federationService.findall()` is mocked to return a predefined list of federations.
     *
     * Preconditions:
     * - `testFederationDTO` is initialized with expected data.
     * - `BASE_PATH` defines the endpoint path for retrieving federations.
     *
     * Exceptions:
     * - Throws `Exception` if the execution of the endpoint request fails or an assertion is not met.
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
     * Tests the `GET /federation/{id}` endpoint to ensure it retrieves a specific federation
     * as a JSON object based on the provided federation ID.
     *
     * This test verifies the following behaviors:
     * 1. The controller interacts with the `federationService` to fetch the federation by its ID.
     * 2. Returns an HTTP status of 200 (OK) if the federation is found.
     * 3. Produces a response with a content type compatible with `application/json`.
     * 4. Returns a JSON object containing the expected fields: `id`, `name`, `country`, and `url`.
     *
     * Assertions:
     * - Verifies that the response status is 200 (OK).
     * - Ensures the response content type is `application/json`.
     * - Validates the JSON structure for correct values of `id`, `name`, `country`, and `url`.
     *
     * Mocks:
     * - `federationService.findById(UUID id)` is mocked to return a predefined `testFederationDTO`.
     *
     * Preconditions:
     * - `federationId` is initialized with a valid UUID.
     * - `testFederationDTO` contains test data matching the `federationId`.
     * - `BASE_PATH` defines the endpoint path for retrieval.
     *
     * Exceptions:
     * - Throws `Exception` if the execution of the endpoint request fails or an assertion is not met.
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
     * Test to verify that fetching a federation by its ID returns a 404 Not Found status
     * when the requested federation is not present in the system.
     *
     * This method mocks the behavior of the federationService to return an empty Optional
     * when searching for a federation with the given ID. It then performs a GET request to
     * the corresponding API endpoint and expects the response to have a 404 status code.
     *
     * @throws Exception if an error occurs during the execution of the request.
     */
    @Test
    void getFederationById_returnsNotFound_whenFederationNotFound() throws Exception {
        given(federationService.findById(federationId)).willReturn(Optional.empty());

        mockMvc.perform(get(BASE_PATH + "/" + federationId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the `POST /federation` endpoint to ensure it successfully creates a new federation
     * with the provided details and invokes the associated service method.
     *
     * This test verifies the following behaviors:
     * 1. The endpoint accepts a JSON request body representing a FederationForm object.
     * 2. Returns an HTTP status of 201 (Created) upon successful creation.
     * 3. Calls the `save(FederationForm federation)` method of the `federationService`
     *    with the deserialized FederationForm object.
     *
     * Assertions:
     * - Verifies that the HTTP response status is 201 (Created).
     * - Confirms that the `federationService.save` method is invoked with any instance
     *   of `FederationForm`, validating the integration between the controller and service layers.
     *
     * Mocks:
     * - `mockMvc` simulates the HTTP request to the `POST /federation` endpoint.
     * - `federationService.save(FederationForm federation)` is mocked to verify its invocation.
     *
     * Preconditions:
     * - `testFederationForm` is a predefined object with valid test data.
     * - `BASE_PATH` defines the endpoint path for creating federations.
     * - `objectMapper` is configured for serializing the `testFederationForm` to JSON.
     *
     * Exceptions:
     * - Throws `Exception` if the execution of the request fails or if any assertions are not met.
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
     * Tests the `PUT /federation/{id}` endpoint to ensure it updates an existing federation
     * with the provided details and invokes the associated service method.
     *
     * This test verifies the following behaviors:
     * 1. The endpoint accepts a JSON request body representing a `FederationForm` object.
     * 2. Calls the `update(UUID id, FederationForm federation)` method of the `federationService`
     *    with the correct federation ID and deserialized `FederationForm` object as parameters.
     * 3. Returns an HTTP status of 204 (No Content) upon successful update.
     *
     * Assertions:
     * - Confirms that the HTTP response status is 204 (No Content).
     * - Validates that the `federationService.update` method is invoked exactly once with
     *   the expected `federationId` and a `FederationForm` object.
     *
     * Mocks:
     * - `mockMvc` simulates the HTTP request to the `PUT /federation/{id}` endpoint.
     * - `federationService.update(UUID id, FederationForm federation)` is mocked to verify its invocation.
     *
     * Preconditions:
     * - `federationId` is initialized with a valid UUID.
     * - `testFederationForm` contains valid data for updating a federation.
     * - `BASE_PATH` defines the endpoint's base path.
     * - `objectMapper` is configured to serialize `testFederationForm` to JSON format.
     *
     * Exceptions:
     * - Throws `Exception` if the request execution fails or if any assertions are not met.
     */
    @Test
    void updateFederation_returnsNoContent_andCallsServiceWithIdAndBody() throws Exception {
        mockMvc.perform(put(BASE_PATH + "/" + federationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFederationDTO)))
                .andExpect(status().isNoContent());

        verify(federationService).update(eq(federationId), any(FederationDTO.class));
    }

    /**
     * Tests the `DELETE /federation/{id}` endpoint to ensure it successfully deletes a federation
     * and invokes the associated service method with the correct federation ID.
     *
     * This test verifies the following behaviors:
     * 1. The endpoint correctly accepts a DELETE request with a federation ID as part of the URL path.
     * 2. Calls the `delete(UUID id)` method of the `federationService` with the provided ID.
     * 3. Returns an HTTP status of 204 (No Content) upon successful execution.
     *
     * Assertions:
     * - Confirms that the HTTP response status is 204 (No Content).
     * - Validates that the `federationService.delete(UUID id)` method is invoked exactly once
     *   with the expected `federationId`.
     *
     * Mocks:
     * - `mockMvc` simulates the HTTP DELETE request to the `/federation/{id}` endpoint.
     * - `federationService.delete(UUID id)` is mocked to verify its invocation.
     *
     * Preconditions:
     * - `federationId` is initialized with a valid UUID.
     * - `BASE_PATH` defines the endpoint's base path for federation operations.
     *
     * Exceptions:
     * - Throws `Exception` if the execution of the request fails or if any assertions are not met.
     */
    @Test
    void deleteFederation_returnsNoContent_andCallsServiceWithId() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/" + federationId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(federationService).delete(eq(federationId));
    }
}