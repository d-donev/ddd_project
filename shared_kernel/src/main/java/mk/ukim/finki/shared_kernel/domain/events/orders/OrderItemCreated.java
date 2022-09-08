package mk.ukim.finki.shared_kernel.domain.events.orders;

import lombok.Getter;
import mk.ukim.finki.shared_kernel.domain.config.TopicHolder;
import mk.ukim.finki.shared_kernel.domain.events.DomainEvent;

@Getter
public class OrderItemCreated extends DomainEvent {

    private String auto_part_id;
    private int quantity;

    public OrderItemCreated(String topic) {
        super(TopicHolder.TOPIC_ORDER_ITEM_ADDED);
    }


    public OrderItemCreated(String productId, int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_ADDED);
        this.auto_part_id = productId;
        this.quantity = quantity;
    }

}
