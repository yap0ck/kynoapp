package com.yapock.kynoapp.pl.Controllers;

import com.yapock.kynoapp.bll.FederationService;
import com.yapock.kynoapp.dal.mappers.FederationMappers;
import com.yapock.kynoapp.pl.federation.FederationDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The FederationController class provides RESTful endpoints for managing federations.
 * This controller handles operations such as listing all federations, retrieving a single federation
 * by its ID, creating a new federation, updating an existing federation, and deleting a federation.
 * It interacts with the FederationService to perform these operations.
 *
 * Endpoints:
 * 1. GET /federation:
 *    Retrieves a list of all federations.
 *
 * 2. GET /federation/{id}:
 *    Retrieves a specific federation by its ID.
 *
 * 3. POST /federation:
 *    Creates a new federation with the provided details.
 *
 * 4. PUT /federation/{id}:
 *    Updates an existing federation identified by its ID with new details.
 *
 * 5. DELETE /federation/{id}:
 *    Deletes a federation identified by its ID.
 *
 * Dependencies:
 * The FederationController depends on the FederationService for handling business logic and data
 * access for federations.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/federation")
public class FederationController {
    /**
     * Service layer dependency responsible for managing federation operations and business logic.
     * This instance provides an abstraction for performing CRUD operations on federations, enabling
     * the controller to interact with the data access layer through a service interface.
     *
     * Responsibilities:
     * - Retrieving a list of all federations.
     * - Fetching federation details by their identifier.
     * - Creating new federations from provided data.
     * - Updating existing federations with new details.
     * - Deleting federations based on their identifier.
     *
     * The FederationService adheres to separation of concerns, encapsulating business rules
     * and logic while ensuring that the controller remains focused on HTTP request handling.
     */
    private final FederationService federationService;

    /**
     * Retrieves a list of all federations.
     *
     * @return a {@code ResponseEntity} containing a list of {@code FederationDTO} objects.
     *         The response will include an HTTP status of 200 (OK) if the operation is successful.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<FederationDTO>> listFederations(){
        return ResponseEntity.ok(federationService.findall());
    }

    /**
     * Retrieves a specific federation by its ID.
     *
     * @param id the unique identifier of the federation to be retrieved.
     * @return a {@code ResponseEntity} containing the details of the federation as a {@code FederationDTO}.
     * If the federation with the specified ID is not found, the response may include an appropriate HTTP status.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<FederationDTO> getFederation(@PathVariable UUID id){
        return ResponseEntity.ofNullable(federationService.findById(id)
                .orElseThrow(() -> new NotFoundException("Federation with ID " + id + " not found")));
    }

    /**
     * Creates a new federation with the provided details.
     * The federation data is validated before being saved.
     *
     * @param federation the {@code FederationDTO} object containing the details of the federation to be created.
     *                   The input must be valid as per the constraints defined in {@code FederationDTO}.
     * @return a {@code ResponseEntity} with an HTTP status of 201 (CREATED) if the federation is successfully created,
     *         and no response body is returned.
     */
    @PostMapping
    public ResponseEntity<Void> saveFederation(@Valid @RequestBody FederationDTO federation) {
        federationService.create(federation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Updates an existing federation identified by its unique ID with the provided details.
     * This method delegates the update operation to the FederationService.
     *
     * @param id the unique identifier of the federation to update
     * @param federation the FederationForm object containing the updated details of the federation
     * @return a ResponseEntity with an HTTP status indicating the outcome of the operation,
     * typically HTTP 204 (NO_CONTENT) if the update is successful
     */
    @PutMapping("/{id}")
    public ResponseEntity updateFederation(@PathVariable UUID id, @Valid @RequestBody FederationDTO
            federation){
        if(federationService.update(id, federation).isEmpty()) throw new NotFoundException("Federation with ID " + id + " not found");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a federation identified by its unique ID.
     * This operation removes the federation from the database if it exists.
     *
     * @param id The unique identifier of the federation to be deleted.
     * @return A ResponseEntity indicating the outcome of the operation.
     *         Returns HTTP status 204 (NO_CONTENT) if the deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFederation(@PathVariable UUID id){
        if (federationService.findById(id).isEmpty()) {
            throw new NotFoundException("Federation with ID " + id + " not found");
        }
        federationService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
