package kitchenpos.fixture;

import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.tablegroup.domain.TableGroup;

public final class TableGroupFixture {

    private Long id;
    private LocalDateTime createdDate;
    private List<OrderTable> orderTables;

    private TableGroupFixture() {
    }

    public static TableGroupFixture builder() {
        return new TableGroupFixture();
    }

    public TableGroupFixture withId(Long id) {
        this.id = id;
        return this;
    }

    public TableGroupFixture withCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public TableGroupFixture withOrderTables(List<OrderTable> orderTables) {
        this.orderTables = orderTables;
        return this;
    }

    public TableGroup build() {
        return new TableGroup(
            id,
            createdDate,
            orderTables == null ? List.of(
                new OrderTable(null, null, 100, true),
                new OrderTable(null, null, 100, true))
                : orderTables
        );
    }
}
