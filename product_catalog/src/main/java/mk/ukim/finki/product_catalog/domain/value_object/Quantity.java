package mk.ukim.finki.product_catalog.domain.value_object;

import lombok.Getter;
import mk.ukim.finki.shared_kernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Quantity implements ValueObject {

    private final int quantity;

    protected Quantity() {
        this.quantity = 0;
    }

}
