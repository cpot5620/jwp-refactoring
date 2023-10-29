package kitchenpos.fixture;

import kitchenpos.product.domain.Product;
import kitchenpos.common.vo.Price;
import kitchenpos.product.ui.dto.ProductRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class ProductFixture {

    public static final String 상품명 = "상품";

    public static Product 상품_엔티티_생성() {
        return new Product(상품명, new Price(BigDecimal.valueOf(10_000)));
    }

    public static List<Product> 상품_엔티티들_생성(final int count) {
        final List<Product> 상품들 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            상품들.add(상품_엔티티_생성());
        }

        return 상품들;
    }

    public static ProductRequest 상품_요청_dto_생성() {
        return new ProductRequest(상품명, BigDecimal.valueOf(10_000));
    }
}
