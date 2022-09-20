package mk.ukim.finki.order_managemant.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.order_managemant.domain.exception.OrderAlreadyExistException;
import mk.ukim.finki.order_managemant.domain.exception.OrderIdDoesNotExistException;
import mk.ukim.finki.order_managemant.domain.exception.OrderItemIdDoesNotExistException;
import mk.ukim.finki.order_managemant.domain.model.*;
import mk.ukim.finki.order_managemant.domain.repository.OrderRepository;
import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPart;
import mk.ukim.finki.order_managemant.service.OrderService;
import mk.ukim.finki.order_managemant.service.forms.OrderForm;
import mk.ukim.finki.order_managemant.service.forms.OrderItemForm;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemDeleted;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.infra.DomainEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrderId placeOrder() {
        Order order = this.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESING)).findFirst().get();
        List<OrderItem> orderItemList = order.getOrderItemSet().stream().toList();
        Currency currency = Currency.MKD;
        List<OrderItemForm> orderItemForms = orderItemList.stream()
            .map(x -> new OrderItemForm(new AutoPart(x.getAutoPartId(), x.getName(), x.getPrice(), 0), x.getQuantity()))
            .collect(Collectors.toList());
        OrderForm orderForm = new OrderForm(currency, orderItemForms);
        Objects.requireNonNull(orderForm, "order form must not be null!");
        var constraintValidations = validator.validate(orderForm);
        if (constraintValidations.size()>0) {
            throw new ConstraintViolationException("Order form is not valid !", constraintValidations);
        }
        order.setOrderState(OrderState.PROCESSED);
        order.setOrderedOn(Instant.now());
        order.setCurrency(Currency.MKD);
        var newOrder = orderRepository.saveAndFlush(order);
        newOrder.getOrderItemSet().forEach(item -> domainEventPublisher.publish(new OrderItemCreated(item.getAutoPartId().getId(),
            item.getQuantity())));
       return new OrderId(newOrder.getId());
    }



    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public void createOrder() {

        if (orderRepository.findAll().stream().anyMatch(x -> x.getOrderState().equals(OrderState.PROCESING))) {
            throw new OrderAlreadyExistException();
        }
        Order order = new Order();
        order.setOrderState(OrderState.PROCESING);
        orderRepository.save(order);
    }


    @Override
    public void addItem(String orderId, OrderItemForm orderItemForm) throws OrderIdDoesNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(
            (OrderIdDoesNotExistException::new)
        );
        order.addItem(orderItemForm.getAutoPart(), orderItemForm.getQuantity());
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getAutoPart().getId().getId(),orderItemForm.getQuantity()));

    }

    @Override
    public void deleteItem(String orderId, String orderItemId) throws OrderIdDoesNotExistException, OrderItemIdDoesNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(
            (OrderIdDoesNotExistException::new)
        );
        order.removeItem(orderItemId);
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemDeleted(orderItemId,1));

    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(Instant.now(),orderForm.getCurrency());
        orderForm.getItems().forEach(item -> order.addItem(item.getAutoPart(), item.getQuantity()));
        return order;
    }
}
