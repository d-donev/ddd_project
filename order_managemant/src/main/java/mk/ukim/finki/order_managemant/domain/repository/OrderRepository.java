package mk.ukim.finki.order_managemant.domain.repository;


import mk.ukim.finki.order_managemant.domain.model.Order;
import mk.ukim.finki.order_managemant.domain.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
