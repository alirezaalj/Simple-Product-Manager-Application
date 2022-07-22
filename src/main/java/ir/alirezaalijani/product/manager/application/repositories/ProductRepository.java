package ir.alirezaalijani.product.manager.application.repositories;

import ir.alirezaalijani.product.manager.application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("select p from Product as p where p.name like CONCAT('%',:name,'%')")
    List<Product> findAllByNameContaining(@Param("name") String name);

    @Query(nativeQuery = true,value = "select ID from PRODUCT ORDER BY ID DESC LIMIT 0, 1")
    Integer getHighestId();
}
