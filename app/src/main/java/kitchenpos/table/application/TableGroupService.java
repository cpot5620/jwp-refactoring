package kitchenpos.table.application;

import kitchenpos.table.application.dto.OrderTableDto;
import kitchenpos.table.application.dto.request.TableGroupCreateRequest;
import kitchenpos.table.application.dto.response.TableGroupResponse;
import kitchenpos.table.application.mapper.TableGroupMapper;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTables;
import kitchenpos.table.domain.TableGroup;
import kitchenpos.order.persistence.OrderRepository;
import kitchenpos.table.persistence.OrderTableRepository;
import kitchenpos.table.persistence.TableGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableGroupService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(final OrderRepository orderRepository, final OrderTableRepository orderTableRepository, final TableGroupRepository tableGroupRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Transactional
    public TableGroupResponse create(final TableGroupCreateRequest request) {
        final List<OrderTableDto> orderTableIds = request.getOrderTables();
        checkRequestOrderTableSize(orderTableIds);
        final List<OrderTable> savedOrderTables = findOrderTables(orderTableIds);
        checkSavedOrderTablesHasSameSizeWithRequest(orderTableIds, savedOrderTables);

        final TableGroup savedTableGroup = tableGroupRepository.save(new TableGroup());
        final OrderTables orderTables = new OrderTables(savedOrderTables);
        orderTables.joinGroup(savedTableGroup);

        return TableGroupMapper.mapToTableGroupResponse(savedTableGroup, orderTables);
    }

    private static void checkRequestOrderTableSize(final List<OrderTableDto> request) {
        if (CollectionUtils.isEmpty(request) || request.size() < 2) {
            throw new IllegalArgumentException();
        }
    }

    private List<OrderTable> findOrderTables(List<OrderTableDto> request) {
        final List<Long> orderTableIds = request.stream()
                .map(OrderTableDto::getId)
                .collect(Collectors.toList());

        return orderTableRepository.findAllByIdIn(orderTableIds);
    }

    private static void checkSavedOrderTablesHasSameSizeWithRequest(List<OrderTableDto> request, List<OrderTable> savedOrderTables) {
        if (request.size() != savedOrderTables.size()) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        final TableGroup tableGroup = findTableGroupById(tableGroupId);
        final OrderTables orderTables = new OrderTables(orderTableRepository.findAllByTableGroupId(tableGroup.getId()));
        validateOrderStatusInTableGroup(orderTables);
        orderTables.leaveGroup();
        orderTableRepository.saveAll(orderTables.getOrderTables());
    }

    private TableGroup findTableGroupById(final Long tableGroupId) {
        return tableGroupRepository.findById(tableGroupId)
                .orElseThrow(IllegalArgumentException::new);
    }

    private void validateOrderStatusInTableGroup(final OrderTables orderTables) {
        if (orderRepository.existsByOrderTableIdInAndOrderStatusIn(
                orderTables.getOrderTableIds(), Arrays.asList(OrderStatus.COOKING, OrderStatus.MEAL))) {
            throw new IllegalArgumentException();
        }
    }
}
