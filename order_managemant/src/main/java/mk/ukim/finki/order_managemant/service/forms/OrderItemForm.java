package mk.ukim.finki.order_managemant.service.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.order_managemant.domain.valueObjects.AutoPart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OrderItemForm {

    @NotNull
    private AutoPart autoPart;

    @Min(1)
    private int quantity = 1;

}
