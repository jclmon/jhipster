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

import com.lastcrm.gateway.domain.Fuente;
import com.lastcrm.gateway.domain.*; // for static metamodels
import com.lastcrm.gateway.repository.FuenteRepository;
import com.lastcrm.gateway.service.dto.FuenteCriteria;
import com.lastcrm.gateway.service.dto.FuenteDTO;
import com.lastcrm.gateway.service.mapper.FuenteMapper;

/**
 * Service for executing complex queries for {@link Fuente} entities in the database.
 * The main input is a {@link FuenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuenteDTO} or a {@link Page} of {@link FuenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuenteQueryService extends QueryService<Fuente> {

    private final Logger log = LoggerFactory.getLogger(FuenteQueryService.class);

    private final FuenteRepository fuenteRepository;

    private final FuenteMapper fuenteMapper;

    public FuenteQueryService(FuenteRepository fuenteRepository, FuenteMapper fuenteMapper) {
        this.fuenteRepository = fuenteRepository;
        this.fuenteMapper = fuenteMapper;
    }

    /**
     * Return a {@link List} of {@link FuenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuenteDTO> findByCriteria(FuenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fuente> specification = createSpecification(criteria);
        return fuenteMapper.toDto(fuenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FuenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuenteDTO> findByCriteria(FuenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fuente> specification = createSpecification(criteria);
        return fuenteRepository.findAll(specification, page)
            .map(fuenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fuente> specification = createSpecification(criteria);
        return fuenteRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Fuente> createSpecification(FuenteCriteria criteria) {
        Specification<Fuente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Fuente_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Fuente_.nombre));
            }
        }
        return specification;
    }
}
