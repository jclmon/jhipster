package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.service.AgenteService;
import com.lastcrm.gateway.web.rest.errors.BadRequestAlertException;
import com.lastcrm.gateway.service.dto.AgenteDTO;
import com.lastcrm.gateway.service.dto.AgenteCriteria;
import com.lastcrm.gateway.service.AgenteQueryService;

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
 * REST controller for managing {@link com.lastcrm.gateway.domain.Agente}.
 */
@RestController
@RequestMapping("/api")
public class AgenteResource {

    private final Logger log = LoggerFactory.getLogger(AgenteResource.class);

    private static final String ENTITY_NAME = "agente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgenteService agenteService;

    private final AgenteQueryService agenteQueryService;

    public AgenteResource(AgenteService agenteService, AgenteQueryService agenteQueryService) {
        this.agenteService = agenteService;
        this.agenteQueryService = agenteQueryService;
    }

    /**
     * {@code POST  /agentes} : Create a new agente.
     *
     * @param agenteDTO the agenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agenteDTO, or with status {@code 400 (Bad Request)} if the agente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agentes")
    public ResponseEntity<AgenteDTO> createAgente(@Valid @RequestBody AgenteDTO agenteDTO) throws URISyntaxException {
        log.debug("REST request to save Agente : {}", agenteDTO);
        if (agenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new agente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgenteDTO result = agenteService.save(agenteDTO);
        return ResponseEntity.created(new URI("/api/agentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agentes} : Updates an existing agente.
     *
     * @param agenteDTO the agenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenteDTO,
     * or with status {@code 400 (Bad Request)} if the agenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agentes")
    public ResponseEntity<AgenteDTO> updateAgente(@Valid @RequestBody AgenteDTO agenteDTO) throws URISyntaxException {
        log.debug("REST request to update Agente : {}", agenteDTO);
        if (agenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgenteDTO result = agenteService.save(agenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agentes} : get all the agentes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agentes in body.
     */
    @GetMapping("/agentes")
    public ResponseEntity<List<AgenteDTO>> getAllAgentes(AgenteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Agentes by criteria: {}", criteria);
        Page<AgenteDTO> page = agenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /agentes/count} : count all the agentes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/agentes/count")
    public ResponseEntity<Long> countAgentes(AgenteCriteria criteria) {
        log.debug("REST request to count Agentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(agenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /agentes/:id} : get the "id" agente.
     *
     * @param id the id of the agenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agentes/{id}")
    public ResponseEntity<AgenteDTO> getAgente(@PathVariable Long id) {
        log.debug("REST request to get Agente : {}", id);
        Optional<AgenteDTO> agenteDTO = agenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agenteDTO);
    }

    /**
     * {@code DELETE  /agentes/:id} : delete the "id" agente.
     *
     * @param id the id of the agenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agentes/{id}")
    public ResponseEntity<Void> deleteAgente(@PathVariable Long id) {
        log.debug("REST request to delete Agente : {}", id);
        agenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
