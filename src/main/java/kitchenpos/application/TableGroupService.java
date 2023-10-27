package kitchenpos.application;

import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.OrderTables;
import kitchenpos.domain.TableGroup;
import kitchenpos.request.TableGroupCreateRequest;
import kitchenpos.request.TableGroupUnitDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableGroupService {
    private final OrderDao orderDao;
    private final OrderTableDao orderTableDao;
    private final TableGroupDao tableGroupDao;

    public TableGroupService(final OrderDao orderDao, final OrderTableDao orderTableDao, final TableGroupDao tableGroupDao) {
        this.orderDao = orderDao;
        this.orderTableDao = orderTableDao;
        this.tableGroupDao = tableGroupDao;
    }

    @Transactional
    public TableGroup create(TableGroupCreateRequest request) {
        List<Long> orderTableIds = request.getOrderTables().stream()
                .map(TableGroupUnitDto::getId)
                .collect(Collectors.toList());

        OrderTables savedOrderTables = new OrderTables(orderTableDao.findAllByIdIn(orderTableIds));
        if (savedOrderTables.size() != orderTableIds.size()) {
            throw new IllegalArgumentException();
        }

        TableGroup savedTableGroup = tableGroupDao.save(TableGroup.from(savedOrderTables));
        savedTableGroup.assignTables(savedOrderTables);
        saveAllTables(savedTableGroup.getOrderTables());

        return savedTableGroup;
    }

    private void saveAllTables(List<OrderTable> orderTables) {
        for (OrderTable orderTable : orderTables) {
            orderTableDao.save(orderTable);
        }
    }

    @Transactional
    public void ungroup(Long tableGroupId) {
        OrderTables orderTables = new OrderTables(orderTableDao.findAllByTableGroupId(tableGroupId));
        validateOrderStatus(orderTables);
        orderTables.ungroup();
        saveAllTables(orderTables.getOrderTables());
    }

    private void validateOrderStatus(OrderTables orderTables) {
        List<Long> orderTableIds = orderTables.getIds();
        List<String> invalidOrderStatusToUngroup = List.of(OrderStatus.COOKING.name(), OrderStatus.MEAL.name());
        if (orderDao.existsByOrderTableIdInAndOrderStatusIn(orderTableIds, invalidOrderStatusToUngroup)) {
            throw new IllegalArgumentException();
        }
    }
}
