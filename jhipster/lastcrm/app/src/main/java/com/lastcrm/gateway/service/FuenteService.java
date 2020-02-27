package com.lastcrm.gateway.service;

import com.lastcrm.gateway.domain.Fuente;
import com.lastcrm.gateway.repository.FuenteRepository;
import com.lastcrm.gateway.service.dto.FuenteDTO;
import com.lastcrm.gateway.service.mapper.FuenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fuente}.
 */
@Service
@Transactional
public class FuenteService {

    private final Logger log = LoggerFactory.getLogger(FuenteService.class);

    private final FuenteRepository fuenteRepository;

    private final FuenteMapper fuenteMapper;

    public FuenteService(FuenteRepository fuenteRepository, FuenteMapper fuenteMapper) {
        this.fuenteRepository = fuenteRepository;
        this.fuenteMapper = fuenteMapper;
    }

    /**
     * Save a fuente.
     *
     * @param fuenteDTO the entity to save.
     * @return the persisted entity.
     */
    public FuenteDTO save(FuenteDTO fuenteDTO) {
        log.debug("Request to save Fuente : {}", fuenteDTO);
        Fuente fuente = fuenteMapper.toEntity(fuenteDTO);
        fuente = fuenteRepository.save(fuente);
        return fuenteMapper.toDto(fuente);
    }

    /**
     * Get all the fuentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FuenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fuentes");
        return fuenteRepository.findAll(pageable)
            .map(fuenteMapper::toDto);
    }


    /**
     * Get one fuente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuenteDTO> findOne(Long id) {
        log.debug("Request to get Fuente : {}", id);
        return fuenteRepository.findById(id)
            .map(fuenteMapper::toDto);
    }

    /**
     * Delete the fuente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fuente : {}", id);
        fuenteRepository.deleteById(id);
    }
}
