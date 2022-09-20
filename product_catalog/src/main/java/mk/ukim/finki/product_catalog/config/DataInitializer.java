package mk.ukim.finki.product_catalog.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.Auto_part;
import mk.ukim.finki.product_catalog.domain.repository.AutoPartRepository;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final AutoPartRepository autoPartRepository;


    @PostConstruct
    public void initData() {
        Auto_part part1 = Auto_part.build("Tire", Money.valueOf(Currency.MKD,3000),4, "", "");
        Auto_part part2 = Auto_part.build("Turbo", Money.valueOf(Currency.MKD,15000), 1, "", "");
        if (autoPartRepository.findAll().isEmpty()) {
            autoPartRepository.saveAll(Arrays.asList(part1,part2));
        }
    }

}
