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

import com.lastcrm.gateway.domain.FichaCliente;
import com.lastcrm.gateway.domain.*; // for static metamodels
import com.lastcrm.gateway.repository.FichaClienteRepository;
import com.lastcrm.gateway.service.dto.FichaClienteCriteria;
import com.lastcrm.gateway.service.dto.FichaClienteDTO;
import com.lastcrm.gateway.service.mapper.FichaClienteMapper;

/**
 * Service for executing complex queries for {@link FichaCliente} entities in the database.
 * The main input is a {@link FichaClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FichaClienteDTO} or a {@link Page} of {@link FichaClienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FichaClienteQueryService extends QueryService<FichaCliente> {

    private final Logger log = LoggerFactory.getLogger(FichaClienteQueryService.class);

    private final FichaClienteRepository fichaClienteRepository;

    private final FichaClienteMapper fichaClienteMapper;

    public FichaClienteQueryService(FichaClienteRepository fichaClienteRepository, FichaClienteMapper fichaClienteMapper) {
        this.fichaClienteRepository = fichaClienteRepository;
        this.fichaClienteMapper = fichaClienteMapper;
    }

    /**
     * Return a {@link List} of {@link FichaClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FichaClienteDTO> findByCriteria(FichaClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FichaCliente> specification = createSpecification(criteria);
        return fichaClienteMapper.toDto(fichaClienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FichaClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FichaClienteDTO> findByCriteria(FichaClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FichaCliente> specification = createSpecification(criteria);
        return fichaClienteRepository.findAll(specification, page)
            .map(fichaClienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FichaClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FichaCliente> specification = createSpecification(criteria);
        return fichaClienteRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<FichaCliente> createSpecification(FichaClienteCriteria criteria) {
        Specification<FichaCliente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FichaCliente_.id));
            }
            if (criteria.getSolicitud() != null) {
                specification = specification.and(buildSpecification(criteria.getSolicitud(), FichaCliente_.solicitud));
            }
            if (criteria.getPrioridad() != null) {
                specification = specification.and(buildSpecification(criteria.getPrioridad(), FichaCliente_.prioridad));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), FichaCliente_.comentario));
            }
            if (criteria.getClienteId() != null) {
                specification = specification.and(buildSpecification(criteria.getClienteId(),
                    root -> root.join(FichaCliente_.cliente, JoinType.LEFT).get(Cliente_.id)));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoId(),
                    root -> root.join(FichaCliente_.producto, JoinType.LEFT).get(Producto_.id)));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(FichaCliente_.areas, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getCitaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCitaId(),
                    root -> root.join(FichaCliente_.citas, JoinType.LEFT).get(Cita_.id)));
            }
            if (criteria.getTipoProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoProductoId(),
                    root -> root.join(FichaCliente_.tipoProductos, JoinType.LEFT).get(TipoProducto_.id)));
            }
        }
        return specification;
    }
}
