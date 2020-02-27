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

import com.lastcrm.gateway.domain.Cliente;
import com.lastcrm.gateway.domain.*; // for static metamodels
import com.lastcrm.gateway.repository.ClienteRepository;
import com.lastcrm.gateway.service.dto.ClienteCriteria;
import com.lastcrm.gateway.service.dto.ClienteDTO;
import com.lastcrm.gateway.service.mapper.ClienteMapper;

/**
 * Service for executing complex queries for {@link Cliente} entities in the database.
 * The main input is a {@link ClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClienteDTO} or a {@link Page} of {@link ClienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClienteQueryService extends QueryService<Cliente> {

    private final Logger log = LoggerFactory.getLogger(ClienteQueryService.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    public ClienteQueryService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Return a {@link List} of {@link ClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> findByCriteria(ClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteMapper.toDto(clienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findByCriteria(ClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteRepository.findAll(specification, page)
            .map(clienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Cliente> createSpecification(ClienteCriteria criteria) {
        Specification<Cliente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Cliente_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Cliente_.nombre));
            }
            if (criteria.getApellido1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellido1(), Cliente_.apellido1));
            }
            if (criteria.getApellido2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellido2(), Cliente_.apellido2));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNif(), Cliente_.nif));
            }
            if (criteria.getGenero() != null) {
                specification = specification.and(buildSpecification(criteria.getGenero(), Cliente_.genero));
            }
            if (criteria.getTlfMovil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTlfMovil(), Cliente_.tlfMovil));
            }
            if (criteria.getTlfMovil2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTlfMovil2(), Cliente_.tlfMovil2));
            }
            if (criteria.getTlfFijo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTlfFijo(), Cliente_.tlfFijo));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Cliente_.fax));
            }
            if (criteria.getEmail1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail1(), Cliente_.email1));
            }
            if (criteria.getEmail2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail2(), Cliente_.email2));
            }
            if (criteria.getFuenteId() != null) {
                specification = specification.and(buildSpecification(criteria.getFuenteId(),
                    root -> root.join(Cliente_.fuente, JoinType.LEFT).get(Fuente_.id)));
            }
        }
        return specification;
    }
}
