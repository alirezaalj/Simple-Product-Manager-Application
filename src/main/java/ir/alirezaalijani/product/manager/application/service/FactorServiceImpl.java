package ir.alirezaalijani.product.manager.application.service;

import ir.alirezaalijani.product.manager.application.model.Factor;
import ir.alirezaalijani.product.manager.application.model.Product;
import ir.alirezaalijani.product.manager.application.repositories.FactorRepository;
import ir.alirezaalijani.product.manager.application.repositories.ProductRepository;
import ir.alirezaalijani.product.manager.application.repositories.SubFactorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("factorService")
public class FactorServiceImpl implements FactorService{

    private final FactorRepository factorRepository;
    private final SubFactorRepository subFactorRepository;
    private final ProductRepository productRepository;

    public FactorServiceImpl(FactorRepository factorRepository,
                             SubFactorRepository subFactorRepository,
                             ProductRepository productRepository){
        this.factorRepository = factorRepository;
        this.subFactorRepository = subFactorRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public boolean addNewFactor(Factor factor) {
        if (factor!=null&&factor.getSubFactors()!=null){
            Factor savedFactor =this.factorRepository.save(factor);
            factor.getSubFactors()
                    .forEach(subFactor -> subFactor.setFactor(savedFactor));
            factor.getSubFactors()
                    .forEach(subFactor -> {
                        subFactor.getProduct().setExistCount(subFactor.getProduct().getExistCount()-subFactor.getCount());
                        Product product=productRepository.save(subFactor.getProduct());
                    });
            int subSize = this.subFactorRepository.saveAll(factor.getSubFactors()).size();
            return savedFactor.getId() > 0 && subSize==factor.getSubFactors().size();
        }
        return false;
    }

    @Override
    public List<Factor> findAll() {
        return factorRepository.findAll();
    }

    @Override
    public List<Factor> findAllToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        return factorRepository.findAllByCreateAtAfter(today.getTime());
    }

    @Override
    public Factor findByCode(int id) {
        return factorRepository.findById(id).orElse(null);
    }
}
