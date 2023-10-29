package kitchenpos.ordertable.ui;

import kitchenpos.ordertable.application.TableService;
import kitchenpos.ordertable.ui.dto.ChangeOrderTableEmptyRequest;
import kitchenpos.ordertable.ui.dto.ChangeOrderTableNumberOfGuestsRequest;
import kitchenpos.ordertable.ui.dto.OrderTableRequest;
import kitchenpos.ordertable.ui.dto.OrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TableRestController {

    private final TableService tableService;

    public TableRestController(final TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/api/tables")
    public ResponseEntity<OrderTableResponse> create(@RequestBody final OrderTableRequest orderTableRequest) {
        final OrderTableResponse created = tableService.create(orderTableRequest);
        final URI uri = URI.create("/api/tables/" + created.getId());
        return ResponseEntity.created(uri)
                             .body(created);
    }

    @GetMapping("/api/tables")
    public ResponseEntity<List<OrderTableResponse>> list() {
        return ResponseEntity.ok()
                             .body(tableService.list());
    }

    @PutMapping("/api/tables/{orderTableId}/empty")
    public ResponseEntity<OrderTableResponse> changeEmpty(
            @PathVariable final Long orderTableId,
            @RequestBody final ChangeOrderTableEmptyRequest changeEmptyRequest
    ) {
        return ResponseEntity.ok()
                             .body(tableService.changeEmpty(orderTableId, changeEmptyRequest));
    }

    @PutMapping("/api/tables/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
            @PathVariable final Long orderTableId,
            @RequestBody final ChangeOrderTableNumberOfGuestsRequest changeNumberOfGuestsRequest
    ) {
        return ResponseEntity.ok()
                             .body(tableService.changeNumberOfGuests(orderTableId, changeNumberOfGuestsRequest));
    }
}
