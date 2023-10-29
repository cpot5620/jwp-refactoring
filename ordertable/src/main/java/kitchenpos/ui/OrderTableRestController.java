package kitchenpos.ui;

import kitchenpos.OrderTable;
import kitchenpos.application.OrderTableService;
import kitchenpos.application.request.ChangeEmptyRequest;
import kitchenpos.application.request.NumberOfGuestsRequest;
import kitchenpos.application.request.OrderTableRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping("/api/tables")
    public ResponseEntity<OrderTable> create(@RequestBody final OrderTableRequest request) {
        final OrderTable created = orderTableService.create(request);
        final URI uri = URI.create("/api/tables/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping("/api/tables")
    public ResponseEntity<List<OrderTable>> list() {
        return ResponseEntity.ok()
                .body(orderTableService.list());
    }

    @PutMapping("/api/tables/{orderTableId}/empty")
    public ResponseEntity<OrderTable> changeEmpty(
            @PathVariable final Long orderTableId,
            @RequestBody final ChangeEmptyRequest request) {
        return ResponseEntity.ok()
                .body(orderTableService.changeEmpty(orderTableId, request));
    }

    @PutMapping("/api/tables/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTable> changeNumberOfGuests(
            @PathVariable final Long orderTableId,
            @RequestBody final NumberOfGuestsRequest request) {
        return ResponseEntity.ok()
                .body(orderTableService.changeNumberOfGuests(orderTableId, request));
    }
}
