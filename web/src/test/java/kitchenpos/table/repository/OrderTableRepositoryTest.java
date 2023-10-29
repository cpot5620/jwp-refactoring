package kitchenpos.table.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import kitchenpos.config.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.TableGroup;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
class OrderTableRepositoryTest {

    @Autowired
    private TableGroupRepository tableGroupRepository;

    @Autowired
    private OrderTableRepository orderTableRepository;

    private TableGroup tableGroup;

    @BeforeEach
    void setUp() {
        TableGroup tableGroupEntity = TableGroup.builder()
                .orderTables(Collections.emptyList())
                .build();
        tableGroup = tableGroupRepository.save(tableGroupEntity);
    }

    @Test
    void 주문_테이블_엔티티를_저장한다() {
        OrderTable orderTableEntity = createOrderTableEntity();

        OrderTable savedOrderTable = orderTableRepository.save(orderTableEntity);

        assertThat(savedOrderTable.getId()).isPositive();
    }

    @Test
    void 주문_테이블_엔티티를_조회한다() {
        OrderTable orderTableEntity = createOrderTableEntity();
        OrderTable savedOrderTable = orderTableRepository.save(orderTableEntity);

        assertThat(orderTableRepository.findById(savedOrderTable.getId())).isPresent();
    }

    @Test
    void 모든_주문_테이블_엔티티를_조회한다() {
        OrderTable orderTableEntityA = createOrderTableEntity();
        OrderTable orderTableEntityB = createOrderTableEntity();
        OrderTable savedOrderTableA = orderTableRepository.save(orderTableEntityA);
        OrderTable savedOrderTableB = orderTableRepository.save(orderTableEntityB);

        List<OrderTable> orderTables = orderTableRepository.findAll();

        assertThat(orderTables).usingRecursiveFieldByFieldElementComparatorOnFields("id")
                .contains(savedOrderTableA, savedOrderTableB);
    }

    @Test
    void ID에_해당하는_주문_테이블_엔티티를_조회한다() {
        OrderTable orderTableEntityA = createOrderTableEntity();
        OrderTable orderTableEntityB = createOrderTableEntity();
        OrderTable savedOrderTableA = orderTableRepository.save(orderTableEntityA);
        OrderTable savedOrderTableB = orderTableRepository.save(orderTableEntityB);

        List<OrderTable> orderTables = orderTableRepository.findAllByIdIn(List.of(savedOrderTableA.getId()));

        assertThat(orderTables).usingRecursiveFieldByFieldElementComparatorOnFields("id")
                .contains(savedOrderTableA)
                .doesNotContain(savedOrderTableB);
    }

    @Test
    void 테이블_그룹_ID에_해당하는_주문_테이블_엔티티를_조회한다() {
        OrderTable orderTableEntityA = createOrderTableEntity();
        OrderTable orderTableEntityB = createOrderTableEntity();
        OrderTable saveOrderTableA = orderTableRepository.save(orderTableEntityA);
        OrderTable saveOrderTableB = orderTableRepository.save(orderTableEntityB);

        List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(tableGroup.getId());

        assertThat(orderTables).usingRecursiveFieldByFieldElementComparatorOnFields("id")
                .contains(saveOrderTableA, saveOrderTableB);
    }

    private OrderTable createOrderTableEntity() {
        return OrderTable.builder()
                .empty(false)
                .tableGroupId(tableGroup.getId())
                .numberOfGuests(10)
                .build();
    }
}
