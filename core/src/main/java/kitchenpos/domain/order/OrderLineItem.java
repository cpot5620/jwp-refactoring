package kitchenpos.domain.order;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kitchenpos.domain.common.Name;
import kitchenpos.domain.common.Price;
import kitchenpos.domain.common.Quantity;

@Entity
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "menu_id")
    private Long menuId;

    private Quantity quantity;

    private Name menuName;

    private Price menuPrice;

    protected OrderLineItem() {
    }

    public OrderLineItem(final Long menuId, final Name menuName, final Price menuPrice, final long quantity) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.quantity = new Quantity(quantity);
    }

    public void initOrder(final Order order) {
        this.order = order;
    }

    public Long getSeq() {
        return seq;
    }

    public Order getOrder() {
        return order;
    }

    public Long getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName.getValue();
    }

    public BigDecimal getMenuPrice() {
        return menuPrice.getValue();
    }

    public long getQuantity() {
        return quantity.value();
    }

    @Override
    public boolean equals(final Object target) {
        if (this == target) {
            return true;
        }
        if (target == null || getClass() != target.getClass()) {
            return false;
        }
        final OrderLineItem targetOrderLineItem = (OrderLineItem) target;
        return Objects.equals(getSeq(), targetOrderLineItem.getSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeq());
    }
}
