package ir.alirezaalijani.product.manager.application.repositories;

import ir.alirezaalijani.product.manager.application.model.Factor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FactorRepository extends JpaRepository<Factor,Integer> {
    List<Factor> findAllByCreateAtAfter(Date from);
}
