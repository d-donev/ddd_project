package mk.ukim.finki.product_catalog.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.Auto_part_id;
import mk.ukim.finki.product_catalog.services.AutoPartService;
import mk.ukim.finki.shared_kernel.domain.config.TopicHolder;
import mk.ukim.finki.shared_kernel.domain.events.DomainEvent;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemDeleted;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AutoPartEventListener {

    private final AutoPartService autoPartService;


    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_ADDED, groupId = "productCatalog")
    public void consumeOrderItemCreatedEvent(String jsonMessage) {
        try {
            OrderItemCreated event = DomainEvent.fromJson(jsonMessage, OrderItemCreated.class);
            autoPartService.orderItemCreated(Auto_part_id.of(event.getAuto_part_id()), event.getQuantity());
        } catch (Exception e) {

        }
    }

    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_DELETED, groupId = "productCatalog")
    public void consumeOrderItemDeletedEvent(String jsonMessage) {
        try {
            OrderItemDeleted event = DomainEvent.fromJson(jsonMessage, OrderItemDeleted.class);
            autoPartService.orderItemRemoved(Auto_part_id.of(event.getAuto_part_id()), event.getQuantity());
        } catch (Exception e) {

        }
    }
}
