package com.lastcrm.gateway.service;

import com.lastcrm.gateway.domain.FichaCliente;
import com.lastcrm.gateway.repository.FichaClienteRepository;
import com.lastcrm.gateway.service.dto.FichaClienteDTO;
import com.lastcrm.gateway.service.mapper.FichaClienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FichaCliente}.
 */
@Service
@Transactional
public class FichaClienteService {

    private final Logger log = LoggerFactory.getLogger(FichaClienteService.class);

    private final FichaClienteRepository fichaClienteRepository;

    private final FichaClienteMapper fichaClienteMapper;

    public FichaClienteService(FichaClienteRepository fichaClienteRepository, FichaClienteMapper fichaClienteMapper) {
        this.fichaClienteRepository = fichaClienteRepository;
        this.fichaClienteMapper = fichaClienteMapper;
    }

    /**
     * Save a fichaCliente.
     *
     * @param fichaClienteDTO the entity to save.
     * @return the persisted entity.
     */
    public FichaClienteDTO save(FichaClienteDTO fichaClienteDTO) {
        log.debug("Request to save FichaCliente : {}", fichaClienteDTO);
        FichaCliente fichaCliente = fichaClienteMapper.toEntity(fichaClienteDTO);
        fichaCliente = fichaClienteRepository.save(fichaCliente);
        return fichaClienteMapper.toDto(fichaCliente);
    }

    /**
     * Get all the fichaClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FichaClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FichaClientes");
        return fichaClienteRepository.findAll(pageable)
            .map(fichaClienteMapper::toDto);
    }

    /**
     * Get all the fichaClientes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FichaClienteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return fichaClienteRepository.findAllWithEagerRelationships(pageable).map(fichaClienteMapper::toDto);
    }
    

    /**
     * Get one fichaCliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FichaClienteDTO> findOne(Long id) {
        log.debug("Request to get FichaCliente : {}", id);
        return fichaClienteRepository.findOneWithEagerRelationships(id)
            .map(fichaClienteMapper::toDto);
    }

    /**
     * Delete the fichaCliente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FichaCliente : {}", id);
        fichaClienteRepository.deleteById(id);
    }
}
