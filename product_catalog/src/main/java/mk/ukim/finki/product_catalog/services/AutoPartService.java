package mk.ukim.finki.product_catalog.services;

import mk.ukim.finki.product_catalog.domain.models.Auto_part;
import mk.ukim.finki.product_catalog.domain.models.Auto_part_id;
import mk.ukim.finki.product_catalog.services.form.AutoPartForm;

import java.util.List;

public interface AutoPartService {

    Auto_part findById(Auto_part_id id);
    Auto_part createProduct(AutoPartForm form);
    Auto_part orderItemCreated(Auto_part_id productId, int quantity);
    Auto_part orderItemRemoved(Auto_part_id productId, int quantity);
    List<Auto_part> getAll();
    void deleteProduct(String id);


}
