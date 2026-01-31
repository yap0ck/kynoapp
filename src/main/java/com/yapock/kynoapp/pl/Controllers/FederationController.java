package com.yapock.kynoapp.pl.Controllers;

import com.yapock.kynoapp.bll.FederationService;
import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.pl.federation.FederationDTO;
import com.yapock.kynoapp.pl.federation.FederationForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@AllArgsConstructor
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
     * Retrieves a list of all federations available in the system.
     *
     * @param model the model object to be used for populating data, if needed.
     * @return a ResponseEntity containing a list of FederationDTO objects representing the federations.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<FederationDTO>> listFederations(Model model){
        return ResponseEntity.ok(federationService.findall());
    }

    /**
     * Retrieves a specific federation by its ID.
     *
     * @param id the unique identifier of the federation to be retrieved.
     * @return a {@code ResponseEntity} containing the details of the federation as a {@code FederationDTO}.
     *         If the federation with the specified ID is not found, the response may include an appropriate HTTP status.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<FederationDTO> getFederation(@PathVariable Long id){
        return ResponseEntity.ok(federationService.findById(id));
    }

    /**
     * Saves a new federation using the provided federation details.
     *
     * @param federation the federation details to be saved, encapsulated in a FederationForm object.
     * @return a ResponseEntity with an HTTP status of CREATED upon successful creation of the federation.
     */
    @PostMapping
    public ResponseEntity saveFederation(@RequestBody FederationForm federation){
        federationService.save(federation);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Updates an existing federation identified by its unique ID with the provided details.
     * This method delegates the update operation to the FederationService.
     *
     * @param id the unique identifier of the federation to update
     * @param federation the FederationForm object containing the updated details of the federation
     * @return a ResponseEntity with an HTTP status indicating the outcome of the operation,
     *         typically HTTP 200 (OK) if the update is successful
     */
    @PutMapping("/{id}")
    public ResponseEntity updateFederation(@PathVariable Long id, @RequestBody FederationForm federation){
        federationService.update(id, federation);
        return new ResponseEntity(HttpStatus.OK);
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
    public ResponseEntity deleteFederation(@PathVariable Long id){
        federationService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
