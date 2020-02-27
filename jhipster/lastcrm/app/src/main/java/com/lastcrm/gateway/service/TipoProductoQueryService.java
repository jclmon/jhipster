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

import com.lastcrm.gateway.domain.TipoProducto;
import com.lastcrm.gateway.domain.*; // for static metamodels
import com.lastcrm.gateway.repository.TipoProductoRepository;
import com.lastcrm.gateway.service.dto.TipoProductoCriteria;
import com.lastcrm.gateway.service.dto.TipoProductoDTO;
import com.lastcrm.gateway.service.mapper.TipoProductoMapper;

/**
 * Service for executing complex queries for {@link TipoProducto} entities in the database.
 * The main input is a {@link TipoProductoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoProductoDTO} or a {@link Page} of {@link TipoProductoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoProductoQueryService extends QueryService<TipoProducto> {

    private final Logger log = LoggerFactory.getLogger(TipoProductoQueryService.class);

    private final TipoProductoRepository tipoProductoRepository;

    private final TipoProductoMapper tipoProductoMapper;

    public TipoProductoQueryService(TipoProductoRepository tipoProductoRepository, TipoProductoMapper tipoProductoMapper) {
        this.tipoProductoRepository = tipoProductoRepository;
        this.tipoProductoMapper = tipoProductoMapper;
    }

    /**
     * Return a {@link List} of {@link TipoProductoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoProductoDTO> findByCriteria(TipoProductoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoProducto> specification = createSpecification(criteria);
        return tipoProductoMapper.toDto(tipoProductoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoProductoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoProductoDTO> findByCriteria(TipoProductoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoProducto> specification = createSpecification(criteria);
        return tipoProductoRepository.findAll(specification, page)
            .map(tipoProductoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoProductoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoProducto> specification = createSpecification(criteria);
        return tipoProductoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<TipoProducto> createSpecification(TipoProductoCriteria criteria) {
        Specification<TipoProducto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoProducto_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), TipoProducto_.nombre));
            }
        }
        return specification;
    }
}
