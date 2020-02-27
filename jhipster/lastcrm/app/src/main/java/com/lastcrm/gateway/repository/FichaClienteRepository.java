package com.lastcrm.gateway.repository;
import com.lastcrm.gateway.domain.FichaCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FichaCliente entity.
 */
@Repository
public interface FichaClienteRepository extends JpaRepository<FichaCliente, Long>, JpaSpecificationExecutor<FichaCliente> {

    @Query(value = "select distinct fichaCliente from FichaCliente fichaCliente left join fetch fichaCliente.areas left join fetch fichaCliente.citas left join fetch fichaCliente.tipoProductos",
        countQuery = "select count(distinct fichaCliente) from FichaCliente fichaCliente")
    Page<FichaCliente> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct fichaCliente from FichaCliente fichaCliente left join fetch fichaCliente.areas left join fetch fichaCliente.citas left join fetch fichaCliente.tipoProductos")
    List<FichaCliente> findAllWithEagerRelationships();

    @Query("select fichaCliente from FichaCliente fichaCliente left join fetch fichaCliente.areas left join fetch fichaCliente.citas left join fetch fichaCliente.tipoProductos where fichaCliente.id =:id")
    Optional<FichaCliente> findOneWithEagerRelationships(@Param("id") Long id);

}
