package mk.ukim.finki.order_managemant.infra;

import lombok.AllArgsConstructor;
import mk.ukim.finki.shared_kernel.domain.events.DomainEvent;
import mk.ukim.finki.shared_kernel.infra.DomainEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(DomainEvent event) {
        this.kafkaTemplate.send(event.getTopic(),event.toJson());
    }
}
