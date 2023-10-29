package kitchenpos.common.vo;

import kitchenpos.exception.InvalidPriceValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    public final static Price ZERO = new Price(BigDecimal.ZERO);

    @Column(nullable = false)
    private BigDecimal value;

    public Price() {}

    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceValue("상품의 가격은 0 혹은 양수여야 합니다.");
        }
    }

    public Price sum(final Price other) {
        this.value = this.value.add(other.value);

        return this;
    }

    public boolean isHigherThan(final Price other) {
        return this.value.compareTo(other.value) > 0;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Price{" +
               "value=" + value +
               '}';
    }
}
