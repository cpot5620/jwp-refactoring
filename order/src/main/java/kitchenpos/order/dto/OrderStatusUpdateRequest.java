package kitchenpos.order.dto;

public class OrderStatusUpdateRequest {

    private String orderStatus;

    public OrderStatusUpdateRequest() {
    }

    public OrderStatusUpdateRequest(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
