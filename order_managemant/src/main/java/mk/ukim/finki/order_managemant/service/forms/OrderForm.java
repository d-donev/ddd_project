package mk.ukim.finki.order_managemant.service.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.order_managemant.domain.model.OrderItem;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderForm {

    @NotNull
    private Currency currency;

    @Valid
    @NotEmpty
    private List<OrderItemForm> items = new ArrayList<>();


}
