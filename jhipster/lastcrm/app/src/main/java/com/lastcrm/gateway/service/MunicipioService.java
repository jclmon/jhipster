package com.lastcrm.gateway.service;

import com.lastcrm.gateway.domain.Municipio;
import com.lastcrm.gateway.repository.MunicipioRepository;
import com.lastcrm.gateway.service.dto.MunicipioDTO;
import com.lastcrm.gateway.service.mapper.MunicipioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Municipio}.
 */
@Service
@Transactional
public class MunicipioService {

    private final Logger log = LoggerFactory.getLogger(MunicipioService.class);

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper municipioMapper;

    public MunicipioService(MunicipioRepository municipioRepository, MunicipioMapper municipioMapper) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
    }

    /**
     * Save a municipio.
     *
     * @param municipioDTO the entity to save.
     * @return the persisted entity.
     */
    public MunicipioDTO save(MunicipioDTO municipioDTO) {
        log.debug("Request to save Municipio : {}", municipioDTO);
        Municipio municipio = municipioMapper.toEntity(municipioDTO);
        municipio = municipioRepository.save(municipio);
        return municipioMapper.toDto(municipio);
    }

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MunicipioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Municipios");
        return municipioRepository.findAll(pageable)
            .map(municipioMapper::toDto);
    }


    /**
     * Get one municipio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MunicipioDTO> findOne(Long id) {
        log.debug("Request to get Municipio : {}", id);
        return municipioRepository.findById(id)
            .map(municipioMapper::toDto);
    }

    /**
     * Delete the municipio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Municipio : {}", id);
        municipioRepository.deleteById(id);
    }
}
