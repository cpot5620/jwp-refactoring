package kitchenpos.tablegroup.application;

import java.util.List;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.tablegroup.domain.TableGroup;
import kitchenpos.tablegroup.domain.TableGroupRepository;
import kitchenpos.tablegroup.dto.TableGroupCreateRequest;
import kitchenpos.tablegroup.dto.TableGroupResponse;
import kitchenpos.tablegroup.vo.GroupTableIds;
import kitchenpos.tablegroup.vo.GroupTables;
import kitchenpos.tablegroup.vo.TableOrders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TableGroupService {

    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(
            OrderTableRepository orderTableRepository,
            OrderRepository orderRepository,
            TableGroupRepository tableGroupRepository
    ) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    public TableGroupResponse create(final TableGroupCreateRequest request) {
        final TableGroup tableGroup = tableGroupRepository.save(request.toTableGroup());

        List<Long> orderTableIds = request.getOrderTableIds();
        final List<OrderTable> orderTables = orderTableRepository.findAllByIdIn(orderTableIds);
        tableGroup.group(orderTables, new GroupTableIds(orderTableIds));

        return TableGroupResponse.of(tableGroup, orderTables);
    }

    public void ungroup(final Long tableGroupId) {
        TableGroup tableGroup = tableGroupRepository.findById(tableGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 단체 지정입니다."));
        List<OrderTable> orderTables = orderTableRepository.findByTableGroupId(tableGroup.getId());
        GroupTables groupTables = new GroupTables(orderTables);
        List<Order> orders = orderRepository.findAllByOrderTableIdIn(groupTables.getOrderTableIds());

        tableGroup.ungroup(groupTables, new TableOrders(orders));
    }
}
