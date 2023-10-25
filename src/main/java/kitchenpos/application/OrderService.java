package kitchenpos.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.application.dto.OrderCreateDto;
import kitchenpos.application.dto.OrderLineItemDto;
import kitchenpos.application.dto.OrderStatusUpdateDto;
import kitchenpos.application.exception.MenuAppException.NotFoundMenuException;
import kitchenpos.application.exception.OrderAppException.NotFoundOrderException;
import kitchenpos.application.exception.OrderAppException.OrderAlreadyCompletedException;
import kitchenpos.application.exception.OrderLineItemAppException.EmptyOrderLineItemException;
import kitchenpos.domain.menu.Menu;
import kitchenpos.domain.menu.MenuRepository;
import kitchenpos.domain.order.Order;
import kitchenpos.domain.order.OrderLineItem;
import kitchenpos.domain.order.OrderRepository;
import kitchenpos.domain.order.OrderStatus;
import kitchenpos.domain.order.OrderTableChangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableChangeService orderTableChangeService;

    public OrderService(
        final OrderRepository orderRepository, final MenuRepository menuRepository,
        final OrderTableChangeService orderTableChangeService) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableChangeService = orderTableChangeService;
    }

    @Transactional
    public Order create(final OrderCreateDto orderCreateDto) {
        final List<OrderLineItemDto> orderLineItemDtos = orderCreateDto.getOrderLineItems();

        if (orderLineItemDtos.isEmpty()) {
            throw new EmptyOrderLineItemException();
        }

        final List<OrderLineItem> orderLineItems = makeOrderLineItems(orderLineItemDtos);

        final Order newOrder = Order.of(orderCreateDto.getOrderTableId(), orderTableChangeService,
            orderLineItems);

        return orderRepository.save(newOrder);
    }

    private List<OrderLineItem> makeOrderLineItems(final List<OrderLineItemDto> orderLineItemDtos) {
        return orderLineItemDtos.stream()
            .map(orderLineItemDto -> {
                final Menu findMenu = menuRepository.findById(orderLineItemDto.getMenuId())
                    .orElseThrow(() -> new NotFoundMenuException(orderLineItemDto.getMenuId()));

                final long quantity = orderLineItemDto.getQuantity();

                return new OrderLineItem(findMenu.getName(), findMenu.getPrice(), quantity);
            })
            .collect(Collectors.toList());
    }

    public List<Order> list() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order changeOrderStatus(final Long orderId,
        final OrderStatusUpdateDto orderStatusUpdateDto) {
        final Order savedOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundOrderException(orderId));

        if (savedOrder.isSameStatus(OrderStatus.COMPLETION)) {
            throw new OrderAlreadyCompletedException(orderId);
        }

        savedOrder.changeOrderStatus(orderStatusUpdateDto.getOrderStatus());

        return orderRepository.save(savedOrder);
    }
}
