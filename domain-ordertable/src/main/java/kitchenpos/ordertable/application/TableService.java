package kitchenpos.ordertable.application;

import kitchenpos.exception.InvalidOrderToChangeEmptyException;
import kitchenpos.exception.NotFoundOrderTableException;
import kitchenpos.ordertable.ui.dto.ChangeOrderTableEmptyRequest;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.domain.OrderTableValidator;
import kitchenpos.ordertable.repository.OrderTableRepository;
import kitchenpos.ordertable.ui.dto.ChangeOrderTableNumberOfGuestsRequest;
import kitchenpos.ordertable.ui.dto.OrderTableRequest;
import kitchenpos.ordertable.ui.dto.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TableService {

    private final OrderTableRepository orderTableRepository;
    private final OrderTableValidator orderTableValidator;

    public TableService(
            final OrderTableRepository orderTableRepository,
            final OrderTableValidator orderTableValidator
    ) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableValidator = orderTableValidator;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableRequest orderTableRequest) {
        final OrderTable savedOrderTable = orderTableRepository.save(orderTableRequest.toEntity());

        return OrderTableResponse.from(savedOrderTable);
    }

    @Transactional(readOnly = true)
    public List<OrderTableResponse> list() {
        final List<OrderTable> orderTables = orderTableRepository.findAll();

        return orderTables.stream()
                          .map(OrderTableResponse::from)
                          .collect(Collectors.toList());
    }

    @Transactional
    public OrderTableResponse changeEmpty(
            final Long orderTableId,
            final ChangeOrderTableEmptyRequest changeEmtpyRequest
    ) {
        final OrderTable orderTable =
                orderTableRepository.findById(orderTableId)
                                    .orElseThrow(() -> new NotFoundOrderTableException("해당 주문 테이블이 존재하지 않습니다."));
        validateOrder(orderTableId, orderTable);

        orderTable.updateEmpty(changeEmtpyRequest.isEmpty());

        return OrderTableResponse.from(orderTable);
    }

    private void validateOrder(final Long orderTableId, final OrderTable orderTable) {
        if (Objects.nonNull(orderTable.getTableGroupId())) {
            throw new InvalidOrderToChangeEmptyException("단체 지정이 정해지지 않아 상태 변경이 불가능합니다.");
        }
        orderTableValidator.validateOrderStatusIsCompletion(orderTableId);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(
            final Long orderTableId,
            final ChangeOrderTableNumberOfGuestsRequest changeNumberOfGuestsRequest
    ) {
        final int numberOfGuests = changeNumberOfGuestsRequest.getNumberOfGuests();
        final OrderTable savedOrderTable =
                orderTableRepository.findById(orderTableId)
                                    .orElseThrow(() -> new NotFoundOrderTableException("해당 주문 테이블이 존재하지 않습니다."));
        savedOrderTable.updateNumberOfGuests(numberOfGuests);

        return OrderTableResponse.from(savedOrderTable);
    }
}
