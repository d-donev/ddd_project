package mk.ukim.finki.product_catalog.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.Auto_part;
import mk.ukim.finki.product_catalog.services.AutoPartService;
import mk.ukim.finki.product_catalog.services.form.AutoPartForm;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AutoPartResource {
    private final AutoPartService autoPartService;


    @GetMapping("/parts")
    public List<Auto_part> getAll() {
        return autoPartService.getAll();
    }


    @GetMapping("/create")
    public String createProduct() {
        AutoPartForm phonePartForm = new AutoPartForm();
        phonePartForm.setAutoPartName("Wheel");
        phonePartForm.setPrice(new Money(Currency.MKD, 10000.0));
        autoPartService.createProduct(phonePartForm);
        return "success";
    }
}
