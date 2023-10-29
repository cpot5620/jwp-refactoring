package kitchenpos.domain.table.repository;

import kitchenpos.domain.RepositoryTestConfig;
import kitchenpos.table.OrderTable;
import kitchenpos.table.TableGroup;
import kitchenpos.table.repository.OrderTableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableRepositoryTest extends RepositoryTestConfig {

    @Autowired
    private OrderTableRepository orderTableRepository;

    @Test
    void findAllByIdIn() {
        // given
        final OrderTable orderTable1 = createOrderTable(null, 4, true);
        final OrderTable orderTable2 = createOrderTable(null, 5, true);

        em.flush();
        em.close();

        // when
        final List<OrderTable> orderTables = orderTableRepository.findAllByIdIn(List.of(orderTable1.getId(), orderTable2.getId()));

        // then
        assertThat(orderTables).containsExactly(orderTable1, orderTable2);
    }

    @Test
    void findAllByTableGroupId() {
        // given
        final TableGroup tableGroup = createTableGroup(LocalDateTime.now());
        final OrderTable orderTable1 = createOrderTable(null, 4, true);
        final OrderTable orderTable2 = createOrderTable(null, 5, true);
        orderTable1.changeGroup(tableGroup);
        orderTable2.changeGroup(tableGroup);

        em.flush();
        em.close();

        // when
        final List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(tableGroup.getId());

        // then
        assertThat(orderTables).containsExactly(orderTable1, orderTable2);
    }
}
