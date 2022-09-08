package mk.ukim.finki.order_managemant.service;

import mk.ukim.finki.order_managemant.domain.exception.OrderIdDoesNotExistException;
import mk.ukim.finki.order_managemant.domain.exception.OrderItemIdDoesNotExistException;
import mk.ukim.finki.order_managemant.domain.model.Order;
import mk.ukim.finki.order_managemant.domain.model.OrderId;
import mk.ukim.finki.order_managemant.domain.model.OrderItemId;
import mk.ukim.finki.order_managemant.service.forms.OrderForm;
import mk.ukim.finki.order_managemant.service.forms.OrderItemForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {


    OrderId placeOrder(OrderForm orderForm);
    List<Order> findAll();
    Optional<Order> findById(OrderId id);

    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdDoesNotExistException;
    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdDoesNotExistException, OrderItemIdDoesNotExistException;

}
