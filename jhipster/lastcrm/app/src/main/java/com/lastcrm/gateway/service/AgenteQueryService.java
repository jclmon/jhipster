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

import com.lastcrm.gateway.domain.Agente;
import com.lastcrm.gateway.domain.*; // for static metamodels
import com.lastcrm.gateway.repository.AgenteRepository;
import com.lastcrm.gateway.service.dto.AgenteCriteria;
import com.lastcrm.gateway.service.dto.AgenteDTO;
import com.lastcrm.gateway.service.mapper.AgenteMapper;

/**
 * Service for executing complex queries for {@link Agente} entities in the database.
 * The main input is a {@link AgenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AgenteDTO} or a {@link Page} of {@link AgenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgenteQueryService extends QueryService<Agente> {

    private final Logger log = LoggerFactory.getLogger(AgenteQueryService.class);

    private final AgenteRepository agenteRepository;

    private final AgenteMapper agenteMapper;

    public AgenteQueryService(AgenteRepository agenteRepository, AgenteMapper agenteMapper) {
        this.agenteRepository = agenteRepository;
        this.agenteMapper = agenteMapper;
    }

    /**
     * Return a {@link List} of {@link AgenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AgenteDTO> findByCriteria(AgenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Agente> specification = createSpecification(criteria);
        return agenteMapper.toDto(agenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AgenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgenteDTO> findByCriteria(AgenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Agente> specification = createSpecification(criteria);
        return agenteRepository.findAll(specification, page)
            .map(agenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Agente> specification = createSpecification(criteria);
        return agenteRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Agente> createSpecification(AgenteCriteria criteria) {
        Specification<Agente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Agente_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Agente_.nombre));
            }
            if (criteria.getApellido1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellido1(), Agente_.apellido1));
            }
            if (criteria.getApellido2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellido2(), Agente_.apellido2));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), Agente_.telefono));
            }
        }
        return specification;
    }
}
