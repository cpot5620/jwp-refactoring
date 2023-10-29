package kitchenpos.ordertable.ui.dto;

import kitchenpos.ordertable.domain.OrderTable;

public class OrderTableRequest {

    private final Integer numberOfGuests;
    private final boolean empty;

    public OrderTableRequest(final Integer numberOfGuests, final boolean empty) {
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public OrderTable toEntity() {
        return OrderTable.of(numberOfGuests, empty);
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
