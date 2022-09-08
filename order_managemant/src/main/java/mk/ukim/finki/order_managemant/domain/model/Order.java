package mk.ukim.finki.order_managemant.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPart;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
public class Order extends AbstractEntity<OrderId> {

    private Instant orderedOn;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Column(name = "order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItemSet;

    public Order(Instant now, @NotNull Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.orderedOn = now;
        this.currency = currency;
    }

    public Order() {
        super(OrderId.randomId(OrderId.class));
    }


    public Money total() {
        return orderItemSet.stream()
            .map(OrderItem::subTotal)
            .reduce(new Money(currency, 0.0),Money::add);
    }

    public OrderItem addItem(@NonNull AutoPart autoPart, int qty) {
        Objects.requireNonNull(autoPart, "auto_part must not be null");
        var item = new OrderItem(autoPart.getId(), autoPart.getPrice(), qty);
        orderItemSet.add(item);
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId, "order item must not be null");
        orderItemSet.removeIf(x -> x.getId().equals(orderItemId));
    }

}
