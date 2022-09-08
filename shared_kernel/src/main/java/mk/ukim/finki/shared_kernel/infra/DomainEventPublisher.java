package mk.ukim.finki.shared_kernel.infra;

import mk.ukim.finki.shared_kernel.domain.events.DomainEvent;

public interface DomainEventPublisher {

    void publish(DomainEvent event);

}
