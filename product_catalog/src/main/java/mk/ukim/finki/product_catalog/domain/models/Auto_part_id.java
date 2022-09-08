package mk.ukim.finki.product_catalog.domain.models;

import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class Auto_part_id extends DomainObjectId {


    protected Auto_part_id() {
        super(Auto_part_id.randomId(Auto_part_id.class).getId());
    }

    public Auto_part_id(@NonNull String uuid) {
        super(uuid);
    }

    public static Auto_part_id of(String uuid) {
        Auto_part_id autoPartId = new Auto_part_id(uuid);
        return autoPartId;
    }
}
