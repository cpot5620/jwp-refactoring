package kitchenpos.tablegroup.dto.request;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import kitchenpos.order.dto.request.OrderTableRequest;

public class TableGroupCreationRequest {

    @NotNull
    private final List<OrderTableRequest> orderTables;

    public TableGroupCreationRequest(List<OrderTableRequest> orderTables) {
        this.orderTables = orderTables;
    }

    public List<OrderTableRequest> getOrderTables() {
        return orderTables;
    }

    public List<Long> getOrderTableIds() {
        return orderTables.stream()
                .map(OrderTableRequest::getId)
                .collect(Collectors.toList());
    }
}
