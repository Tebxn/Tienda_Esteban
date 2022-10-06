package com.tienda.repository;
import com.tienda.entity.Pais;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author ESTEBAN
 */
@Repository
public interface PaisRepository extends CrudRepository<Pais,Long>{   
}
