package mk.ukim.finki.order_managemant.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.order_managemant.domain.exception.OrderAlreadyExistException;
import mk.ukim.finki.order_managemant.domain.exception.OrderIdDoesNotExistException;
import mk.ukim.finki.order_managemant.domain.model.Order;
import mk.ukim.finki.order_managemant.domain.model.OrderItem;
import mk.ukim.finki.order_managemant.domain.model.OrderState;
import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPart;
import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPartId;
import mk.ukim.finki.order_managemant.service.OrderService;
import mk.ukim.finki.order_managemant.service.forms.OrderForm;
import mk.ukim.finki.order_managemant.service.forms.OrderItemForm;
import mk.ukim.finki.product_catalog.services.AutoPartService;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.hibernate.mapping.Collection;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@AllArgsConstructor
public class OrderResource {

    private final OrderService orderService;


    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }


    @PostMapping("/createOrder")
    public String createOrder() {
        try {
            orderService.createOrder();
        }
        catch (OrderAlreadyExistException ex) {
            return "Oreder already exist exception!";
        }
        return "success";
    }

    @PostMapping("/addOrderItem")
    public String addOrderItem(@RequestParam String autoPartId, @RequestParam String name, @RequestParam Double price) {
        Order order = orderService.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESING)).findFirst().get();
        OrderItemForm form = new OrderItemForm(new AutoPart(new AutoPartId(autoPartId), name, new Money(Currency.MKD, price), 1),1);
        orderService.addItem(order.getId(),form);
        return "success";
    }


    @GetMapping("/orderItems")
    public List<OrderItem> getOrderItems() {
        Set<OrderItem> orderItemSet;
        Order order = orderService.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESING)).findFirst().get();
        orderItemSet = order.getOrderItemSet();
        List<OrderItem> list = new ArrayList<>(orderItemSet);
        Collections.sort(list, new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem o1, OrderItem o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return  list;
    }


    @GetMapping("/view/{id}")
    public Order getAllOrderItems(@PathVariable String id) {
        return orderService.findById(id).orElseThrow(OrderIdDoesNotExistException::new);
    }

    @GetMapping("/placeOrder")
    public void placeOrder() {
        orderService.placeOrder();
    }

    @PostMapping("/delete")
    public String deleteOrderItem(@RequestParam String id) {
        var order = orderService.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESING)).findFirst().get();
        var orderItem = order.getOrderItemSet().stream().filter(x -> x.getId().equals(id)).findFirst().get();
        orderService.deleteItem(order.getId(), orderItem.getId());
        return "success";
    }

}
