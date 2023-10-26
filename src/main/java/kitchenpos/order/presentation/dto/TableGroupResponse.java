package kitchenpos.order.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.order.domain.TableGroup;

public class TableGroupResponse {

    private final Long id;

    private final LocalDateTime createdDate;

    private final List<OrderTableResponse> orderTables;

    public TableGroupResponse(final Long id,
                              final LocalDateTime createdDate,
                              final List<OrderTableResponse> orderTables) {
        this.id = id;
        this.createdDate = createdDate;
        this.orderTables = orderTables;
    }

    public static TableGroupResponse from(final TableGroup tableGroup) {
        return new TableGroupResponse(tableGroup.getId(),
                                      tableGroup.getCreatedDate(),
                                      OrderTableResponse.convertToList(tableGroup.getOrderTables()));
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public List<OrderTableResponse> getOrderTables() {
        return orderTables;
    }
}
