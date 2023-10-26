package kitchenpos.application.mapper;

import kitchenpos.application.dto.response.OrderTableResponse;
import kitchenpos.application.dto.response.TableGroupResponse;
import kitchenpos.domain.TableGroup;
import java.util.List;
import java.util.stream.Collectors;

public class TableGroupMapper {

    private TableGroupMapper() {
    }

    public static TableGroupResponse mapToResponse(final TableGroup tableGroup) {
        final List<OrderTableResponse> orderTableResponses = tableGroup.getOrderTables()
                .getValues()
                .stream()
                .map(it -> new OrderTableResponse(it.getId(), it.getNumberOfGuests(), it.isEmpty()))
                .collect(Collectors.toList());
        return new TableGroupResponse(tableGroup.getId(), tableGroup.getCreatedDate(), orderTableResponses);
    }
}
