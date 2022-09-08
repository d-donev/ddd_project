package mk.ukim.finki.order_managemant.domain.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPartId;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name= "order_items")
@Getter
public class OrderItem extends AbstractEntity<OrderItemId> {

    @Embedded
    private Money price;

    private int quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "auto_part_id", nullable = false))
    private AutoPartId autoPartId;

    public OrderItem() {

    }


    public AutoPartId getAutoPartId() {
        return autoPartId;
    }

    public OrderItem(@NonNull AutoPartId autoPartId, @NonNull Money price, int qty) {
        super(DomainObjectId.randomId(OrderItemId.class));
        this.autoPartId = autoPartId;
        this.price = price;
        this.quantity = qty;
    }

    public Money getPrice() {
        return price;
    }

    public Money subTotal() {
        return price.multiply(quantity);
    }


}
