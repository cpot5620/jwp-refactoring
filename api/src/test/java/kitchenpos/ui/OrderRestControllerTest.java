package kitchenpos.ui;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kitchenpos.api.order.ui.OrderRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import kitchenpos.core.order.application.OrderService;
import kitchenpos.core.order.application.dto.OrderLineItemResponse;
import kitchenpos.core.order.application.dto.OrderResponse;
import kitchenpos.core.order.domain.Order;
import kitchenpos.core.order.domain.OrderLineItem;
import kitchenpos.core.order.domain.OrderStatus;
import kitchenpos.core.order.application.dto.OrderLineItemsRequest;
import kitchenpos.core.order.application.OrderRequest;
import kitchenpos.core.product.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ReflectionUtils;

@WebMvcTest(OrderRestController.class)
class OrderRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @Test
    @DisplayName("POST /api/orders")
    void createOrder() throws Exception {
        final List<OrderLineItemsRequest> orderLineItemsRequests = List.of(
                new OrderLineItemsRequest(1L, 4L),
                new OrderLineItemsRequest(2L, 7L)
        );
        final OrderRequest orderRequest = new OrderRequest(1L, orderLineItemsRequests);

        final OrderLineItem orderLineItem = new OrderLineItem(1L, new Price(new BigDecimal("4000")), "메뉴1", 4L);
        final Field field = ReflectionUtils.findField(OrderLineItem.class, "seq");
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, orderLineItem, 1L);
        final OrderLineItem orderLineItem2 = new OrderLineItem(2L, new Price(new BigDecimal("4000")), "메뉴", 7L);
        ReflectionUtils.setField(field, orderLineItem2, 2L);

        when(orderService.create(1L, orderLineItemsRequests))
                .thenReturn(
                        OrderResponse.from(new Order(1L, 1L, OrderStatus.COOKING, LocalDateTime.now(),
                                List.of(orderLineItem, orderLineItem2))
                        ));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("PUT /api/orders/{orderId}/order-status")
    void changeStatusOfOrder() throws Exception {
        final OrderLineItemResponse orderLineItemResponse = new OrderLineItemResponse(1L, 3L, 3L);
        final OrderLineItemResponse orderLineItemResponse2 = new OrderLineItemResponse(2L, 3L, 3L);
        final OrderResponse orderResponse = new OrderResponse(3L, 3L, OrderStatus.MEAL, LocalDateTime.now(),
                List.of(orderLineItemResponse, orderLineItemResponse2));
        when(orderService.changeOrderStatus(3L, OrderStatus.MEAL)).thenReturn(orderResponse);

        mockMvc.perform(put("/api/orders/{orderId}/order-status", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("orderStatus", "MEAL"))))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(orderResponse)))
                .andDo(print());

    }
}
