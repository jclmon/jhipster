package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.service.FichaClienteService;
import com.lastcrm.gateway.web.rest.errors.BadRequestAlertException;
import com.lastcrm.gateway.service.dto.FichaClienteDTO;
import com.lastcrm.gateway.service.dto.FichaClienteCriteria;
import com.lastcrm.gateway.service.FichaClienteQueryService;

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
 * REST controller for managing {@link com.lastcrm.gateway.domain.FichaCliente}.
 */
@RestController
@RequestMapping("/api")
public class FichaClienteResource {

    private final Logger log = LoggerFactory.getLogger(FichaClienteResource.class);

    private static final String ENTITY_NAME = "fichaCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FichaClienteService fichaClienteService;

    private final FichaClienteQueryService fichaClienteQueryService;

    public FichaClienteResource(FichaClienteService fichaClienteService, FichaClienteQueryService fichaClienteQueryService) {
        this.fichaClienteService = fichaClienteService;
        this.fichaClienteQueryService = fichaClienteQueryService;
    }

    /**
     * {@code POST  /ficha-clientes} : Create a new fichaCliente.
     *
     * @param fichaClienteDTO the fichaClienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fichaClienteDTO, or with status {@code 400 (Bad Request)} if the fichaCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ficha-clientes")
    public ResponseEntity<FichaClienteDTO> createFichaCliente(@Valid @RequestBody FichaClienteDTO fichaClienteDTO) throws URISyntaxException {
        log.debug("REST request to save FichaCliente : {}", fichaClienteDTO);
        if (fichaClienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new fichaCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaClienteDTO result = fichaClienteService.save(fichaClienteDTO);
        return ResponseEntity.created(new URI("/api/ficha-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ficha-clientes} : Updates an existing fichaCliente.
     *
     * @param fichaClienteDTO the fichaClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaClienteDTO,
     * or with status {@code 400 (Bad Request)} if the fichaClienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fichaClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ficha-clientes")
    public ResponseEntity<FichaClienteDTO> updateFichaCliente(@Valid @RequestBody FichaClienteDTO fichaClienteDTO) throws URISyntaxException {
        log.debug("REST request to update FichaCliente : {}", fichaClienteDTO);
        if (fichaClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaClienteDTO result = fichaClienteService.save(fichaClienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaClienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ficha-clientes} : get all the fichaClientes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fichaClientes in body.
     */
    @GetMapping("/ficha-clientes")
    public ResponseEntity<List<FichaClienteDTO>> getAllFichaClientes(FichaClienteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FichaClientes by criteria: {}", criteria);
        Page<FichaClienteDTO> page = fichaClienteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /ficha-clientes/count} : count all the fichaClientes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/ficha-clientes/count")
    public ResponseEntity<Long> countFichaClientes(FichaClienteCriteria criteria) {
        log.debug("REST request to count FichaClientes by criteria: {}", criteria);
        return ResponseEntity.ok().body(fichaClienteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ficha-clientes/:id} : get the "id" fichaCliente.
     *
     * @param id the id of the fichaClienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fichaClienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ficha-clientes/{id}")
    public ResponseEntity<FichaClienteDTO> getFichaCliente(@PathVariable Long id) {
        log.debug("REST request to get FichaCliente : {}", id);
        Optional<FichaClienteDTO> fichaClienteDTO = fichaClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaClienteDTO);
    }

    /**
     * {@code DELETE  /ficha-clientes/:id} : delete the "id" fichaCliente.
     *
     * @param id the id of the fichaClienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ficha-clientes/{id}")
    public ResponseEntity<Void> deleteFichaCliente(@PathVariable Long id) {
        log.debug("REST request to delete FichaCliente : {}", id);
        fichaClienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
