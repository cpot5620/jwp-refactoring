package kitchenpos.fixture;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.ordertable.domain.Order;
import kitchenpos.ordertable.domain.OrderLineItem;

public final class OrderFixture {

    private Long id;
    private Long orderTableId;
    private String orderStatus;
    private LocalDateTime orderedTime;
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    private OrderFixture() {
    }

    public static OrderFixture builder() {
        return new OrderFixture();
    }

    public OrderFixture withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderFixture withOrderTableId(Long orderTableId) {
        this.orderTableId = orderTableId;
        return this;
    }

    public OrderFixture withOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderFixture withOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
        return this;
    }

    public OrderFixture withOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
        return this;
    }

    public Order build() {
        Order order = new Order(
            id,
            orderStatus,
            orderTableId,
            orderedTime,
            orderLineItems
        );
        return order;
    }
}
