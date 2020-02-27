package com.lastcrm.gateway.service;

import com.lastcrm.gateway.domain.Agente;
import com.lastcrm.gateway.repository.AgenteRepository;
import com.lastcrm.gateway.service.dto.AgenteDTO;
import com.lastcrm.gateway.service.mapper.AgenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Agente}.
 */
@Service
@Transactional
public class AgenteService {

    private final Logger log = LoggerFactory.getLogger(AgenteService.class);

    private final AgenteRepository agenteRepository;

    private final AgenteMapper agenteMapper;

    public AgenteService(AgenteRepository agenteRepository, AgenteMapper agenteMapper) {
        this.agenteRepository = agenteRepository;
        this.agenteMapper = agenteMapper;
    }

    /**
     * Save a agente.
     *
     * @param agenteDTO the entity to save.
     * @return the persisted entity.
     */
    public AgenteDTO save(AgenteDTO agenteDTO) {
        log.debug("Request to save Agente : {}", agenteDTO);
        Agente agente = agenteMapper.toEntity(agenteDTO);
        agente = agenteRepository.save(agente);
        return agenteMapper.toDto(agente);
    }

    /**
     * Get all the agentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agentes");
        return agenteRepository.findAll(pageable)
            .map(agenteMapper::toDto);
    }


    /**
     * Get one agente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgenteDTO> findOne(Long id) {
        log.debug("Request to get Agente : {}", id);
        return agenteRepository.findById(id)
            .map(agenteMapper::toDto);
    }

    /**
     * Delete the agente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Agente : {}", id);
        agenteRepository.deleteById(id);
    }
}
