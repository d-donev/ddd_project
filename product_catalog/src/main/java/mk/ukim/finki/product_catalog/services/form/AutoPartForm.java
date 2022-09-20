package mk.ukim.finki.product_catalog.services.form;

import lombok.Data;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

@Data
public class AutoPartForm {

    private String autoPartName;
    private Money price;
    private int sales;
    private String imageUrl;
    private String desc;

}
