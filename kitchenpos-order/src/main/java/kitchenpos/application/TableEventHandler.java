package kitchenpos.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderTable;
import kitchenpos.event.TableGroupCreateEvent;
import kitchenpos.event.TableGroupDeleteEvent;
import kitchenpos.event.ValidateSameSizeOrderTableEvent;
import kitchenpos.repository.OrderRepository;
import kitchenpos.repository.OrderTableRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class TableEventHandler {

    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public TableEventHandler(
            final OrderTableRepository orderTableRepository,
            final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @EventListener
    @Transactional
    public void validateSameSizeOrderTable(final ValidateSameSizeOrderTableEvent event) {
        final List<Long> orderTableIds = event.getOrderTableIds();
        final List<OrderTable> orderTables = orderTableRepository.findAllByIdIn(orderTableIds);

        if (orderTableIds.size() != orderTables.size()) {
            throw new IllegalArgumentException("[ERROR] 저장된 데이터의 수와 실제 주문 테이블의 수가 다릅니다.");
        }
    }

    @EventListener
    @Transactional
    public void createTableGroupEvent(final TableGroupCreateEvent event) {
        final Long tableGroupId = event.getTableGroupId();
        final List<OrderTable> orderTables = orderTableRepository.findAllByIdIn(event.getOrderTableIds());

        validateSizeOrderTables(orderTables);
        validateEmptyOrderTables(orderTables);
        updateTableGroupInOrderTable(tableGroupId, orderTables);
    }

    private void validateSizeOrderTables(final List<OrderTable> orderTables) {
        if (CollectionUtils.isEmpty(orderTables) || orderTables.size() < 2) {
            throw new IllegalArgumentException("[ERROR] 주문하는 테이블이 없거나, 단체 주문 대상이 아닙니다.");
        }
    }

    private void validateEmptyOrderTables(final List<OrderTable> orderTables) {
        orderTables.forEach(this::validateEmptyOrderTable);
        orderTables.forEach(this::validateAlreadyJoinAnotherTableGroup);
    }

    private void validateEmptyOrderTable(final OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 테이블이 생성되지 않았습니다.");
        }
    }

    private void validateAlreadyJoinAnotherTableGroup(final OrderTable orderTable) {
        if (Objects.nonNull(orderTable.getTableGroupId())) {
            throw new IllegalArgumentException("[ERROR] 이미 다른 테이블 그룹에 속하는 테이블입니다.");
        }
    }

    private void updateTableGroupInOrderTable(final Long tableGroupId, final List<OrderTable> orderTables) {
        for (final OrderTable orderTable : orderTables) {
            orderTable.group(tableGroupId);
        }
    }

    @EventListener
    @Transactional
    public void deleteTableGroup(final TableGroupDeleteEvent event) {
        final Long tableGroupId = event.getTableGroupId();
        validateOrderIsNotCompletionInOrderTable(tableGroupId);
        updateUngroupOrderTableDto(tableGroupId);
    }

    private void validateOrderIsNotCompletionInOrderTable(final Long tableGroupId) {
        final List<Long> orderTableIds = orderTableRepository.findAllByTableGroupId(tableGroupId).stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());
        final List<Order> orders = orderRepository.findAllByOrderTableIdIn(orderTableIds);
        orders.forEach(this::validateCompletion);
    }

    private void validateCompletion(final Order order) {
        if (order.isNotCompletion()) {
            throw new IllegalArgumentException("[ERROR] 아직 모든 주문이 완료되지 않았습니다.");
        }
    }

    private void updateUngroupOrderTableDto(final Long tableGroupId) {
        final List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(tableGroupId);
        orderTables.forEach(OrderTable::ungroup);
    }
}
