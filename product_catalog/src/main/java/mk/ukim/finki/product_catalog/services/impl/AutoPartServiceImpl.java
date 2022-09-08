package mk.ukim.finki.product_catalog.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.exceptions.AutoPartNotFoundException;
import mk.ukim.finki.product_catalog.domain.models.Auto_part;
import mk.ukim.finki.product_catalog.domain.models.Auto_part_id;
import mk.ukim.finki.product_catalog.domain.repository.AutoPartRepository;
import mk.ukim.finki.product_catalog.services.AutoPartService;
import mk.ukim.finki.product_catalog.services.form.AutoPartForm;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AutoPartServiceImpl implements AutoPartService {

    private final AutoPartRepository autoPartRepository;

    @Override
    public Auto_part findById(Auto_part_id id) {
        return autoPartRepository.findById(id).orElseThrow(AutoPartNotFoundException::new);
    }

    @Override
    public Auto_part createProduct(AutoPartForm form) {
        Auto_part part = Auto_part.build(form.getAutoPartName(),form.getPrice(),form.getSales());
        autoPartRepository.save(part);
        return part;
    }

    @Override
    public Auto_part orderItemCreated(Auto_part_id productId, int quantity) {
        Auto_part part = autoPartRepository.findById(productId).orElseThrow(AutoPartNotFoundException::new);
        part.addSales(quantity);
        autoPartRepository.saveAndFlush(part);
        return part;
    }

    @Override
    public Auto_part orderItemRemoved(Auto_part_id productId, int quantity) {
        Auto_part part = autoPartRepository.findById(productId).orElseThrow(AutoPartNotFoundException::new);
        part.removeSales(quantity);
        autoPartRepository.saveAndFlush(part);
        return part;
    }

    @Override
    public List<Auto_part> getAll() {
        return autoPartRepository.findAll();
    }
}
