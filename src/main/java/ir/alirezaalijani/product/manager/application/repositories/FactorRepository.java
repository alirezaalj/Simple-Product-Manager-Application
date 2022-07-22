package ir.alirezaalijani.product.manager.application.repositories;

import ir.alirezaalijani.product.manager.application.model.Factor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactorRepository extends JpaRepository<Factor,Integer> {
}
