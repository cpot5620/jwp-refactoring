package kitchenpos.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import kitchenpos.ordertable.domain.Order;
import kitchenpos.ordertable.domain.OrderStatus;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.repository.OrderRepository;
import kitchenpos.repository.support.RepositoryTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OrderRepositoryTest extends RepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 특정_주문_테이블중_특정_주문_상태의_주문_존재_확인() {
        // given
        Order order = defaultOrder();

        // when
        boolean cookingOrderExist =
            orderRepository.existsByOrderTableIdAndOrderStatusIn(
                order.getOrderTableId(),
                List.of(OrderStatus.COOKING.name()));

        boolean mealingOrderExist =
            orderRepository.existsByOrderTableIdAndOrderStatusIn(
                order.getOrderTableId(),
                List.of(OrderStatus.MEAL.name()));

        // then
        assertSoftly(softAssertions -> {
            assertThat(cookingOrderExist).isTrue();
            assertThat(mealingOrderExist).isFalse();
        });
    }

    @Test
    void 주문_테이블들_중_특정_주문_상태의_주문_존재_확인() {
        // given
        Order order = defaultOrder();
        List<OrderTable> newOrderTable = makeOrderTable();

        // when
        boolean actual = orderRepository.existsByOrderTableIdInAndOrderStatusIn(
            List.of(order.getOrderTableId(), newOrderTable.get(0).getId()),
            List.of(OrderStatus.COOKING.name()));

        // then
        assertThat(actual).isTrue();
    }

}
