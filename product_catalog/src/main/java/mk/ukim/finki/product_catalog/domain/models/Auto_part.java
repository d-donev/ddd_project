package mk.ukim.finki.product_catalog.domain.models;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name = "auto_part")
@Getter
public class Auto_part extends AbstractEntity<Auto_part_id> {

    private String part_name;

    @AttributeOverrides({
        @AttributeOverride(name="amount", column = @Column(name="price_amount")),
        @AttributeOverride(name="currency", column = @Column(name="price_currency"))
    })
    @Embedded
    private Money price;

    private int num_sales;

    public Auto_part() {
        super(Auto_part_id.randomId(Auto_part_id.class));
    }


    public Money getPrice() {
        return price;
    }



    public static Auto_part build(String productName, Money price, int sales) {
        Auto_part p = new Auto_part();
        p.price = price;
        p.part_name = productName;
        p.num_sales = sales;
        return p;
    }

    public void addSales(int qty) {
        this.num_sales = this.num_sales + qty;
    }

    public void removeSales(int qty) {
        this.num_sales -= qty;
    }



}
