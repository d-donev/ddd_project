package mk.ukim.finki.order_managemant.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.order_managemant.domain.exception.OrderIdDoesNotExistException;
import mk.ukim.finki.order_managemant.domain.exception.OrderItemIdDoesNotExistException;
import mk.ukim.finki.order_managemant.domain.model.Order;
import mk.ukim.finki.order_managemant.domain.model.OrderId;
import mk.ukim.finki.order_managemant.domain.model.OrderItemId;
import mk.ukim.finki.order_managemant.domain.repository.OrderRepository;
import mk.ukim.finki.order_managemant.service.OrderService;
import mk.ukim.finki.order_managemant.service.forms.OrderForm;
import mk.ukim.finki.order_managemant.service.forms.OrderItemForm;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemDeleted;
import mk.ukim.finki.shared_kernel.infra.DomainEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrderId placeOrder(OrderForm orderForm) {
        Objects.requireNonNull(orderForm, "order form must not be null!");
        var constraintValidations = validator.validate(orderForm);
        if (constraintValidations.size()>0) {
            throw new ConstraintViolationException("Order form is not valid !", constraintValidations);
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        newOrder.getOrderItemSet().forEach(item -> domainEventPublisher.publish(new OrderItemCreated(item.getAutoPartId().getId(),
            item.getQuantity())));
        return newOrder.getId();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return orderRepository.findById(id);
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdDoesNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(
            (OrderIdDoesNotExistException::new)
        );
        order.addItem(orderItemForm.getAutoPart(), orderItemForm.getQuantity());
        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getAutoPart().getId().getId(),orderItemForm.getQuantity()));
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdDoesNotExistException, OrderItemIdDoesNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(
            (OrderIdDoesNotExistException::new)
        );
        order.removeItem(orderItemId);
        domainEventPublisher.publish(new OrderItemDeleted(orderItemId.getId(),1));
        orderRepository.saveAndFlush(order);
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(Instant.now(),orderForm.getCurrency());
        orderForm.getItems().forEach(item -> order.addItem(item.getAutoPart(), item.getQuantity()));
        return order;
    }
}
