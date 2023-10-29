package kitchenpos.domain;

import static kitchenpos.order.OrderStatus.COMPLETION;
import static kitchenpos.order.OrderStatus.COOKING;
import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.order.Order;
import kitchenpos.order.OrderStatus;
import kitchenpos.ordertable.OrderTable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void 빈_테이블인_경우_예외가_발생한다() {
        // given
        OrderTable orderTable = new OrderTable(true);

        // when, then
        Assertions.assertThatThrownBy(() -> new Order(orderTable, COOKING))
                .isInstanceOf(IllegalArgumentException.class);
    }

//    @Test
//    void 주문항목이_비어있는_경우_예외가_발생한다() {
//        // given
//        OrderTable orderTable = new OrderTable(false);
//        Order order = new Order(orderTable, COOKING);
//
//        // when, then
//        assertThatThrownBy(() -> order.setOrderLineItems(null))
//                .isInstanceOf(IllegalArgumentException.class);
//    }

    @Nested
    class 주문_상태_변경 {
        @Test
        void 주문_상태를_변경한다() {
            // given
            OrderTable orderTable = new OrderTable(false);
            Order order = new Order(orderTable, COOKING);

            // when
            order.changeOrderStatus(OrderStatus.COMPLETION);

            // then
            assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETION);
        }

        @Test
        void 계산완료_상태인_경우_예외가_발생한다() {
            // given
            OrderTable orderTable = new OrderTable(false);
            Order order = new Order(orderTable, COMPLETION);

            // when, then
            Assertions.assertThatThrownBy(() -> order.changeOrderStatus(OrderStatus.MEAL))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
