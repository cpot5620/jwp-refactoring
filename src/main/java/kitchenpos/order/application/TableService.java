package kitchenpos.order.application;


import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.order.application.dto.TableEmptyRequest;
import kitchenpos.order.application.dto.TableGuestRequest;
import kitchenpos.order.application.dto.TableRequest;
import kitchenpos.order.application.dto.TableResponse;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TableService {
    private final OrderTableRepository orderTableRepository;

    public TableService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }


    public TableResponse create(final TableRequest tableRequest) {
        OrderTable orderTable = tableRequest.toOrderTable();
        OrderTable savedOrderTable = orderTableRepository.save(orderTable);

        return TableResponse.from(savedOrderTable);
    }

    @Transactional(readOnly = true)
    public List<TableResponse> list() {
        return orderTableRepository.findAll().stream()
                .map(TableResponse::from)
                .collect(Collectors.toList());
    }

    public TableResponse changeEmpty(final Long orderTableId, final TableEmptyRequest tableEmptyRequest) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);

        orderTable.changeEmpty(tableEmptyRequest.isEmpty());
        OrderTable savedOrderTable = orderTableRepository.save(orderTable);

        return TableResponse.from(savedOrderTable);
    }

    public TableResponse changeNumberOfGuests(final Long orderTableId, final TableGuestRequest tableGuestRequest) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);

        orderTable.changeNumberOfGuests(tableGuestRequest.getNumberOfGuests());
        OrderTable savedOrderTable = orderTableRepository.save(orderTable);

        return TableResponse.from(savedOrderTable);
    }

    public void groupTable(List<Long> tableIds) {
        final List<OrderTable> savedOrderTables = orderTableRepository.findAllByIdIn(tableIds);

        if (tableIds.size() != savedOrderTables.size()) {
            throw new IllegalArgumentException();
        }
    }
}
