package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.service.FuenteService;
import com.lastcrm.gateway.web.rest.errors.BadRequestAlertException;
import com.lastcrm.gateway.service.dto.FuenteDTO;
import com.lastcrm.gateway.service.dto.FuenteCriteria;
import com.lastcrm.gateway.service.FuenteQueryService;

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
 * REST controller for managing {@link com.lastcrm.gateway.domain.Fuente}.
 */
@RestController
@RequestMapping("/api")
public class FuenteResource {

    private final Logger log = LoggerFactory.getLogger(FuenteResource.class);

    private static final String ENTITY_NAME = "fuente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuenteService fuenteService;

    private final FuenteQueryService fuenteQueryService;

    public FuenteResource(FuenteService fuenteService, FuenteQueryService fuenteQueryService) {
        this.fuenteService = fuenteService;
        this.fuenteQueryService = fuenteQueryService;
    }

    /**
     * {@code POST  /fuentes} : Create a new fuente.
     *
     * @param fuenteDTO the fuenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuenteDTO, or with status {@code 400 (Bad Request)} if the fuente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuentes")
    public ResponseEntity<FuenteDTO> createFuente(@Valid @RequestBody FuenteDTO fuenteDTO) throws URISyntaxException {
        log.debug("REST request to save Fuente : {}", fuenteDTO);
        if (fuenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new fuente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuenteDTO result = fuenteService.save(fuenteDTO);
        return ResponseEntity.created(new URI("/api/fuentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuentes} : Updates an existing fuente.
     *
     * @param fuenteDTO the fuenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuenteDTO,
     * or with status {@code 400 (Bad Request)} if the fuenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuentes")
    public ResponseEntity<FuenteDTO> updateFuente(@Valid @RequestBody FuenteDTO fuenteDTO) throws URISyntaxException {
        log.debug("REST request to update Fuente : {}", fuenteDTO);
        if (fuenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FuenteDTO result = fuenteService.save(fuenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fuentes} : get all the fuentes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuentes in body.
     */
    @GetMapping("/fuentes")
    public ResponseEntity<List<FuenteDTO>> getAllFuentes(FuenteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Fuentes by criteria: {}", criteria);
        Page<FuenteDTO> page = fuenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fuentes/count} : count all the fuentes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fuentes/count")
    public ResponseEntity<Long> countFuentes(FuenteCriteria criteria) {
        log.debug("REST request to count Fuentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(fuenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fuentes/:id} : get the "id" fuente.
     *
     * @param id the id of the fuenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuentes/{id}")
    public ResponseEntity<FuenteDTO> getFuente(@PathVariable Long id) {
        log.debug("REST request to get Fuente : {}", id);
        Optional<FuenteDTO> fuenteDTO = fuenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuenteDTO);
    }

    /**
     * {@code DELETE  /fuentes/:id} : delete the "id" fuente.
     *
     * @param id the id of the fuenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuentes/{id}")
    public ResponseEntity<Void> deleteFuente(@PathVariable Long id) {
        log.debug("REST request to delete Fuente : {}", id);
        fuenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
