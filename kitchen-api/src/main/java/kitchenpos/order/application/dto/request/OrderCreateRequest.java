package kitchenpos.order.application.dto.request;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItems;

import java.util.List;
import java.util.stream.Collectors;

public class OrderCreateRequest {

    private Long orderTableId;

    private List<OrderLineItemCreateRequest> orderLineItems;

    public OrderCreateRequest(final Long orderTableId,
                              final List<OrderLineItemCreateRequest> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public OrderCreateRequest() {
    }

    public Order toOrder() {
        return new Order(
                orderTableId,
                new OrderLineItems(
                        orderLineItems.stream()
                                .map(OrderLineItemCreateRequest::toOrderLineItem)
                                .collect(Collectors.toList())
                )
        );
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemCreateRequest> getOrderLineItems() {
        return orderLineItems;
    }

}
