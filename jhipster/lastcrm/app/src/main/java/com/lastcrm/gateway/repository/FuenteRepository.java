package com.lastcrm.gateway.repository;
import com.lastcrm.gateway.domain.Fuente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fuente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuenteRepository extends JpaRepository<Fuente, Long>, JpaSpecificationExecutor<Fuente> {

}
