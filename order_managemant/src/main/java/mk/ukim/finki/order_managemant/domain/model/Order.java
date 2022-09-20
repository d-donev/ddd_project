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
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
public class Order  {

    private Instant orderedOn;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    @Id
    private String id;
    @Column(name = "order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItemSet;

    public Order(Instant now, @NotNull Currency currency) {
        this.orderedOn = now;
        this.currency = currency;
    }

    public Order() {
        id = genereateID();
    }

    public Instant getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(Instant orderedOn) {
        this.orderedOn = orderedOn;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Set<OrderItem> getOrderItemSet() {
        return orderItemSet;
    }

    public void setOrderItemSet(Set<OrderItem> orderItemSet) {
        this.orderItemSet = orderItemSet;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Money total() {
        return orderItemSet.stream()
            .map(OrderItem::subTotal)
            .reduce(new Money(currency, 0.0),Money::add);
    }

    public OrderItem addItem(@NonNull AutoPart autoPart, int qty) {
        Objects.requireNonNull(autoPart, "auto_part must not be null");
        var item = new OrderItem(autoPart.getId(), autoPart.getPrice(), qty, autoPart.getName());
        orderItemSet.add(item);
        return item;
    }
    public String genereateID() {
        Random random = new Random();
        int temp = random.nextInt()*1000;
        return "jkbs-bdkj-"+temp+"-jb7%-bjk-kb5ds-ivbbes-7j^6";
    }
    public void removeItem(@NonNull String orderItemId) {
        Objects.requireNonNull(orderItemId, "order item must not be null");
        orderItemSet.removeIf(x -> x.getId().equals(orderItemId));
    }

}
