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

import com.lastcrm.gateway.domain.Producto;
import com.lastcrm.gateway.domain.*; // for static metamodels
import com.lastcrm.gateway.repository.ProductoRepository;
import com.lastcrm.gateway.service.dto.ProductoCriteria;
import com.lastcrm.gateway.service.dto.ProductoDTO;
import com.lastcrm.gateway.service.mapper.ProductoMapper;

/**
 * Service for executing complex queries for {@link Producto} entities in the database.
 * The main input is a {@link ProductoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductoDTO} or a {@link Page} of {@link ProductoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductoQueryService extends QueryService<Producto> {

    private final Logger log = LoggerFactory.getLogger(ProductoQueryService.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    public ProductoQueryService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    /**
     * Return a {@link List} of {@link ProductoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductoDTO> findByCriteria(ProductoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Producto> specification = createSpecification(criteria);
        return productoMapper.toDto(productoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findByCriteria(ProductoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Producto> specification = createSpecification(criteria);
        return productoRepository.findAll(specification, page)
            .map(productoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Producto> specification = createSpecification(criteria);
        return productoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Producto> createSpecification(ProductoCriteria criteria) {
        Specification<Producto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Producto_.id));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), Producto_.direccion));
            }
            if (criteria.getDestino() != null) {
                specification = specification.and(buildSpecification(criteria.getDestino(), Producto_.destino));
            }
            if (criteria.getPrecio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrecio(), Producto_.precio));
            }
            if (criteria.getPrecioAnterior() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrecioAnterior(), Producto_.precioAnterior));
            }
            if (criteria.getDormitorios() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDormitorios(), Producto_.dormitorios));
            }
            if (criteria.getAseos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAseos(), Producto_.aseos));
            }
            if (criteria.getMetros() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMetros(), Producto_.metros));
            }
            if (criteria.getGarage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGarage(), Producto_.garage));
            }
            if (criteria.getAnioconstruccion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnioconstruccion(), Producto_.anioconstruccion));
            }
            if (criteria.getMunicipioId() != null) {
                specification = specification.and(buildSpecification(criteria.getMunicipioId(),
                    root -> root.join(Producto_.municipio, JoinType.LEFT).get(Municipio_.id)));
            }
            if (criteria.getTipoProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoProductoId(),
                    root -> root.join(Producto_.tipoProducto, JoinType.LEFT).get(TipoProducto_.id)));
            }
        }
        return specification;
    }
}
