package kitchenpos.core.menu.domain;

import java.math.BigDecimal;
import kitchenpos.core.product.domain.Price;
import kitchenpos.core.product.domain.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;

public class MenuProduct {
    @Id
    private Long seq;
    @Embedded.Empty
    private Price price;
    private AggregateReference<Product, Long> productId;
    private long quantity;

    @PersistenceCreator
    public MenuProduct(final Long seq, final Price price, final Long productId,
                       final long quantity) {
        this.seq = seq;
        this.price = price;
        this.productId = () -> productId;
        this.quantity = quantity;
    }

    public MenuProduct(final Price price, final Long productId, final long quantity) {
        this.price = price;
        this.productId = () -> productId;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getProductId() {
        return productId.getId();
    }

    public long getQuantity() {
        return quantity;
    }

    public Price calculateTotalPrice() {
        return this.price.multiply(new BigDecimal(this.quantity));
    }
}
