package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.service.ProvinciaService;
import com.lastcrm.gateway.web.rest.errors.BadRequestAlertException;
import com.lastcrm.gateway.service.dto.ProvinciaDTO;
import com.lastcrm.gateway.service.dto.ProvinciaCriteria;
import com.lastcrm.gateway.service.ProvinciaQueryService;

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
 * REST controller for managing {@link com.lastcrm.gateway.domain.Provincia}.
 */
@RestController
@RequestMapping("/api")
public class ProvinciaResource {

    private final Logger log = LoggerFactory.getLogger(ProvinciaResource.class);

    private static final String ENTITY_NAME = "provincia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvinciaService provinciaService;

    private final ProvinciaQueryService provinciaQueryService;

    public ProvinciaResource(ProvinciaService provinciaService, ProvinciaQueryService provinciaQueryService) {
        this.provinciaService = provinciaService;
        this.provinciaQueryService = provinciaQueryService;
    }

    /**
     * {@code POST  /provincias} : Create a new provincia.
     *
     * @param provinciaDTO the provinciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provinciaDTO, or with status {@code 400 (Bad Request)} if the provincia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provincias")
    public ResponseEntity<ProvinciaDTO> createProvincia(@Valid @RequestBody ProvinciaDTO provinciaDTO) throws URISyntaxException {
        log.debug("REST request to save Provincia : {}", provinciaDTO);
        if (provinciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new provincia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvinciaDTO result = provinciaService.save(provinciaDTO);
        return ResponseEntity.created(new URI("/api/provincias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /provincias} : Updates an existing provincia.
     *
     * @param provinciaDTO the provinciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provinciaDTO,
     * or with status {@code 400 (Bad Request)} if the provinciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provinciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provincias")
    public ResponseEntity<ProvinciaDTO> updateProvincia(@Valid @RequestBody ProvinciaDTO provinciaDTO) throws URISyntaxException {
        log.debug("REST request to update Provincia : {}", provinciaDTO);
        if (provinciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProvinciaDTO result = provinciaService.save(provinciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provinciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /provincias} : get all the provincias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provincias in body.
     */
    @GetMapping("/provincias")
    public ResponseEntity<List<ProvinciaDTO>> getAllProvincias(ProvinciaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Provincias by criteria: {}", criteria);
        Page<ProvinciaDTO> page = provinciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /provincias/count} : count all the provincias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/provincias/count")
    public ResponseEntity<Long> countProvincias(ProvinciaCriteria criteria) {
        log.debug("REST request to count Provincias by criteria: {}", criteria);
        return ResponseEntity.ok().body(provinciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /provincias/:id} : get the "id" provincia.
     *
     * @param id the id of the provinciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provinciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provincias/{id}")
    public ResponseEntity<ProvinciaDTO> getProvincia(@PathVariable Long id) {
        log.debug("REST request to get Provincia : {}", id);
        Optional<ProvinciaDTO> provinciaDTO = provinciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provinciaDTO);
    }

    /**
     * {@code DELETE  /provincias/:id} : delete the "id" provincia.
     *
     * @param id the id of the provinciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provincias/{id}")
    public ResponseEntity<Void> deleteProvincia(@PathVariable Long id) {
        log.debug("REST request to delete Provincia : {}", id);
        provinciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
