package kitchenpos.core.order.repository;

import kitchenpos.core.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default Order getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
    }

    List<Order> findByOrderTableId(Long orderTableId);
}
