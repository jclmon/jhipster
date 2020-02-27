package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.service.CitaService;
import com.lastcrm.gateway.web.rest.errors.BadRequestAlertException;
import com.lastcrm.gateway.service.dto.CitaDTO;
import com.lastcrm.gateway.service.dto.CitaCriteria;
import com.lastcrm.gateway.service.CitaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lastcrm.gateway.domain.Cita}.
 */
@RestController
@RequestMapping("/api")
public class CitaResource {

    private final Logger log = LoggerFactory.getLogger(CitaResource.class);

    private static final String ENTITY_NAME = "cita";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitaService citaService;

    private final CitaQueryService citaQueryService;

    public CitaResource(CitaService citaService, CitaQueryService citaQueryService) {
        this.citaService = citaService;
        this.citaQueryService = citaQueryService;
    }

    /**
     * {@code POST  /citas} : Create a new cita.
     *
     * @param citaDTO the citaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citaDTO, or with status {@code 400 (Bad Request)} if the cita has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citas")
    public ResponseEntity<CitaDTO> createCita(@Valid @RequestBody CitaDTO citaDTO) throws URISyntaxException {
        log.debug("REST request to save Cita : {}", citaDTO);
        if (citaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cita cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitaDTO result = citaService.save(citaDTO);
        return ResponseEntity.created(new URI("/api/citas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citas} : Updates an existing cita.
     *
     * @param citaDTO the citaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citaDTO,
     * or with status {@code 400 (Bad Request)} if the citaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citas")
    public ResponseEntity<CitaDTO> updateCita(@Valid @RequestBody CitaDTO citaDTO) throws URISyntaxException {
        log.debug("REST request to update Cita : {}", citaDTO);
        if (citaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CitaDTO result = citaService.save(citaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /citas} : get all the citas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citas in body.
     */
    @GetMapping("/citas")
    public ResponseEntity<List<CitaDTO>> getAllCitas(CitaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Citas by criteria: {}", criteria);
        Page<CitaDTO> page = citaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /citas/count} : count all the citas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/citas/count")
    public ResponseEntity<Long> countCitas(CitaCriteria criteria) {
        log.debug("REST request to count Citas by criteria: {}", criteria);
        return ResponseEntity.ok().body(citaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /citas/:id} : get the "id" cita.
     *
     * @param id the id of the citaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citas/{id}")
    public ResponseEntity<CitaDTO> getCita(@PathVariable Long id) {
        log.debug("REST request to get Cita : {}", id);
        Optional<CitaDTO> citaDTO = citaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citaDTO);
    }

    /**
     * {@code DELETE  /citas/:id} : delete the "id" cita.
     *
     * @param id the id of the citaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citas/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        log.debug("REST request to delete Cita : {}", id);
        citaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
