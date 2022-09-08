package mk.ukim.finki.order_managemant.domain.model;

import lombok.NonNull;
import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;

public class OrderId extends DomainObjectId {
    protected OrderId(@NonNull String uuid) {
        super(uuid);
    }
}
