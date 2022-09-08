package mk.ukim.finki.order_managemant.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.shared_kernel.domain.base.ValueObject;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

@Getter
public class AutoPart implements ValueObject {
    private final AutoPartId id;
    private final String name;
    private final Money price;
    private final int num_of_sales;

    private AutoPart() {
        this.num_of_sales = 0;
        this.id = AutoPartId.randomId(AutoPartId.class);
        this.name = "";
        this.price = Money.valueOf(Currency.MKD,0);
    }

    @JsonCreator
    public AutoPart(@JsonProperty("id") AutoPartId id,
                    @JsonProperty("autoPartName") String name,
                    @JsonProperty("price") Money price,
                    @JsonProperty("sales") int num_of_sales) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num_of_sales = num_of_sales;
    }
}
