package kitchenpos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    private String name;

    protected ProductName() {
    }

    public ProductName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 공백이 될 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
