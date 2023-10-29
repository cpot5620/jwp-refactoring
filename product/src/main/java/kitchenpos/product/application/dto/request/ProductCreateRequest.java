package kitchenpos.product.application.dto.request;

import java.math.BigDecimal;

public class ProductCreateRequest {

    final String name;
    final BigDecimal price;

    public ProductCreateRequest(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
