package mk.ukim.finki.order_managemant.domain.valueObjects;

import lombok.NonNull;
import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class AutoPartId extends DomainObjectId {
    public AutoPartId(@NonNull String uuid) {
        super(uuid);
    }

    public AutoPartId() {
        super();
    }
}
