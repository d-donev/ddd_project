package mk.ukim.finki.shared_kernel.domain.financial;


import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.shared_kernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
@Getter
public class Money implements ValueObject {

    @Enumerated(value = EnumType.STRING)
    private final Currency currency;

    private final Double amount;

    protected Money() {
        this.currency = null;
        this.amount = 0.0;
    }

    public Money (@NonNull Currency currency, @NonNull Double amount) {
        this.amount = amount;
        this.currency = currency;
    }


    public static Money valueOf(Currency currency, int amount) {
        return new Money(currency,(double)amount);
    }

    public Money add(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot add with different currencies");
        }
        return new Money(currency,amount + money.amount);
    }

    public Money subtract(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot subtract with different currencies");
        }
        return new Money(currency,amount - money.amount);
    }

    public Money multiply(int m)  {
        return new Money(currency,amount*m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }


}
