package kitchenpos.ordertable.service;

import static kitchenpos.common.exception.ExceptionType.ORDER_TABLE_CANNOT_CHANGE_STATUS;
import static kitchenpos.common.exception.ExceptionType.ORDER_TABLE_NOT_FOUND;

import kitchenpos.common.exception.CustomException;
import java.util.List;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {

    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;

    public OrderTableService(
        final OrderTableRepository orderTableRepository,
        final OrderRepository orderRepository
    ) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderTable create(final OrderTable orderTable) {
        return orderTableRepository.save(orderTable);
    }

    public List<OrderTable> list() {
        return orderTableRepository.findAll();
    }

    @Transactional
    public OrderTable changeEmpty(final Long orderTableId, final OrderTable otherOrderTable) {
        final OrderTable orderTable = getById(orderTableId);

        validateOrderNotProcessing(orderTableId);

        orderTable.changeEmpty(otherOrderTable.isEmpty());

        return orderTable;
    }

    private void validateOrderNotProcessing(Long orderTableId) {
        List<Order> cookingOrders = orderRepository.findByOrderTableIdAndOrderStatus(orderTableId,
            OrderStatus.COOKING);
        List<Order> mealOrders = orderRepository.findByOrderTableIdAndOrderStatus(orderTableId,
            OrderStatus.MEAL);

        if (!cookingOrders.isEmpty() || !mealOrders.isEmpty()) {
            throw new CustomException(ORDER_TABLE_CANNOT_CHANGE_STATUS);
        }
    }

    @Transactional
    public OrderTable changeNumberOfGuests(
        final Long orderTableId,
        final OrderTable otherOrderTable
    ) {
        OrderTable orderTable = getById(orderTableId);

        orderTable.changeNumberOfGuests(otherOrderTable.getNumberOfGuests());

        return orderTable;
    }

    public OrderTable getById(Long orderTableId) {
        return orderTableRepository.findById(orderTableId)
                                   .orElseThrow(() -> new CustomException(ORDER_TABLE_NOT_FOUND));
    }
}
