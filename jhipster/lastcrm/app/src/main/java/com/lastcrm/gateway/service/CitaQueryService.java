package com.lastcrm.gateway.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.lastcrm.gateway.domain.Cita;
import com.lastcrm.gateway.domain.*; // for static metamodels
import com.lastcrm.gateway.repository.CitaRepository;
import com.lastcrm.gateway.service.dto.CitaCriteria;
import com.lastcrm.gateway.service.dto.CitaDTO;
import com.lastcrm.gateway.service.mapper.CitaMapper;

/**
 * Service for executing complex queries for {@link Cita} entities in the database.
 * The main input is a {@link CitaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitaDTO} or a {@link Page} of {@link CitaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitaQueryService extends QueryService<Cita> {

    private final Logger log = LoggerFactory.getLogger(CitaQueryService.class);

    private final CitaRepository citaRepository;

    private final CitaMapper citaMapper;

    public CitaQueryService(CitaRepository citaRepository, CitaMapper citaMapper) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
    }

    /**
     * Return a {@link List} of {@link CitaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitaDTO> findByCriteria(CitaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cita> specification = createSpecification(criteria);
        return citaMapper.toDto(citaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CitaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitaDTO> findByCriteria(CitaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cita> specification = createSpecification(criteria);
        return citaRepository.findAll(specification, page)
            .map(citaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cita> specification = createSpecification(criteria);
        return citaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Cita> createSpecification(CitaCriteria criteria) {
        Specification<Cita> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Cita_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Cita_.fecha));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), Cita_.comentario));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Cita_.estado));
            }
            if (criteria.getAgenteId() != null) {
                specification = specification.and(buildSpecification(criteria.getAgenteId(),
                    root -> root.join(Cita_.agente, JoinType.LEFT).get(Agente_.id)));
            }
        }
        return specification;
    }
}
