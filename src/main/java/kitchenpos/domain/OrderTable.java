package kitchenpos.domain;

import java.util.Objects;

public class OrderTable {
    private final Long id;
    private final Long tableGroupId;
    private final int numberOfGuests;
    private final boolean empty;

    private OrderTable(Long id, Long tableGroupId, int numberOfGuests, boolean empty) {
        validate(numberOfGuests);
        this.id = id;
        this.tableGroupId = tableGroupId;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    private void validate(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public static OrderTableBuilder builder() {
        return new OrderTableBuilder();
    }

    public OrderTable fillTable(Long tableGroupId) {
        return new OrderTable(id, tableGroupId, numberOfGuests, true);
    }

    public OrderTable ungroup() {
        return new OrderTable(id, null, numberOfGuests, true);
    }

    public OrderTable updateEmpty(boolean empty) {
        if (Objects.nonNull(tableGroupId)) {
            throw new IllegalArgumentException();
        }
        return new OrderTable(id, tableGroupId, numberOfGuests, empty);
    }

    public OrderTable updateNumberOfGuests(int numberOfGuests) {
        if (empty) {
            throw new IllegalArgumentException();
        }
        return new OrderTable(id, tableGroupId, numberOfGuests, empty);
    }

    public static final class OrderTableBuilder {
        private Long id;
        private Long tableGroupId;
        private int numberOfGuests;
        private boolean empty;

        private OrderTableBuilder() {
        }

        public OrderTableBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderTableBuilder tableGroupId(Long tableGroupId) {
            this.tableGroupId = tableGroupId;
            return this;
        }

        public OrderTableBuilder numberOfGuests(int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;
            return this;
        }

        public OrderTableBuilder empty(boolean empty) {
            this.empty = empty;
            return this;
        }

        public OrderTable build() {
            return new OrderTable(id, tableGroupId, numberOfGuests, empty);
        }
    }
}
