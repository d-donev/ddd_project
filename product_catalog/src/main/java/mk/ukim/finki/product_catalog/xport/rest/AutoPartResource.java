package mk.ukim.finki.product_catalog.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.Auto_part;
import mk.ukim.finki.product_catalog.domain.models.Auto_part_id;
import mk.ukim.finki.product_catalog.services.AutoPartService;
import mk.ukim.finki.product_catalog.services.form.AutoPartForm;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AutoPartResource {
    private final AutoPartService autoPartService;


    @GetMapping("/parts")
    public List<Auto_part> getAll() {
        return autoPartService.getAll();
    }


    @PostMapping("/create")
    public String createProduct(@RequestParam String partName, @RequestParam Double price, @RequestParam String imageUrl, @RequestParam String description ) {
        AutoPartForm phonePartForm = new AutoPartForm();
        phonePartForm.setAutoPartName(partName);
        phonePartForm.setImageUrl(imageUrl);
        phonePartForm.setDesc(description);
        phonePartForm.setPrice(new Money(Currency.MKD, price));
        autoPartService.createProduct(phonePartForm);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String createProduct(@PathVariable String id) {
        autoPartService.deleteProduct(id);
        return "success";
    }

    @PostMapping("/addSales")
    public String addSales(@RequestParam String id) {
        autoPartService.orderItemCreated(Auto_part_id.of(id), 1);
        return "success";
    }

    @PostMapping("/removeSales")
    public String removeSales(@RequestParam String id) {
        autoPartService.orderItemRemoved(Auto_part_id.of(id), 1);
        return "success";
    }

}
