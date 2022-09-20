package mk.ukim.finki.order_managemant.domain.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPartId;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name= "order_items")
@Getter
@Setter
public class OrderItem   {

    @Embedded
    private Money price;

    private int quantity;
    @Id
    private String id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "auto_part_id", nullable = false))
    private AutoPartId autoPartId;
    private String name;

    public OrderItem() {

    }


    public AutoPartId getAutoPartId() {
        return autoPartId;
    }

    public OrderItem(@NonNull AutoPartId autoPartId, @NonNull Money price, int qty, String name) {
        id = genereateID();
        this.autoPartId = autoPartId;
        this.price = price;
        this.quantity = qty;
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }
    public String genereateID() {
        Random random = new Random();
        int temp = random.nextInt()*1000;
        return "jkbs-bdkj-"+temp+"-jb7%-bjk-kb5ds-ivbbes-7j^6";
    }
    public Money subTotal() {
        return price.multiply(quantity);
    }


}
