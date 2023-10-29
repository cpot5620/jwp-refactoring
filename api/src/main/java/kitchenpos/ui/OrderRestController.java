package kitchenpos.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.application.OrderService;
import kitchenpos.application.dto.ChangeOrderDto;
import kitchenpos.application.dto.CreateOrderDto;
import kitchenpos.ui.dto.request.CreateOrderRequest;
import kitchenpos.ui.dto.request.UpdateOrderStatusRequest;
import kitchenpos.ui.dto.response.CreateOrderResponse;
import kitchenpos.ui.dto.response.ReadOrderResponse;
import kitchenpos.ui.dto.response.UpdateOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<CreateOrderResponse> create(@RequestBody final CreateOrderRequest request) {
        final CreateOrderDto createOrderDto = orderService.create(request);
        final URI uri = URI.create("/api/orders/" + createOrderDto.getId());
        final CreateOrderResponse response = new CreateOrderResponse(createOrderDto);

        return ResponseEntity.created(uri)
                             .body(response);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<ReadOrderResponse>> list() {
        final List<ReadOrderResponse> responses = orderService.list()
                                                              .stream()
                                                              .map(ReadOrderResponse::new)
                                                              .collect(Collectors.toList());

        return ResponseEntity.ok()
                             .body(responses);
    }

    @PutMapping("/api/orders/{orderId}/order-status")
    public ResponseEntity<UpdateOrderResponse> changeOrderStatus(
            @PathVariable final Long orderId,
            @RequestBody final UpdateOrderStatusRequest request
    ) {
        final ChangeOrderDto changeOrderDto = orderService.changeOrderStatus(orderId, request);
        final UpdateOrderResponse response = new UpdateOrderResponse(changeOrderDto);

        return ResponseEntity.ok(response);
    }
}
