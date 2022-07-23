package ir.alirezaalijani.product.manager.application.service;

import ir.alirezaalijani.product.manager.application.model.Factor;

import java.util.List;

public interface FactorService {
    boolean addNewFactor(Factor factor);
    List<Factor> findAll();
    List<Factor> findAllToday();
    Factor findByCode(int id);
}
