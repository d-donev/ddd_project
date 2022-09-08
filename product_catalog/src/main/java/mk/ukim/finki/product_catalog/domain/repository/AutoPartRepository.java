package mk.ukim.finki.product_catalog.domain.repository;

import mk.ukim.finki.product_catalog.domain.models.Auto_part;
import mk.ukim.finki.product_catalog.domain.models.Auto_part_id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoPartRepository extends JpaRepository<Auto_part, Auto_part_id> {


}
