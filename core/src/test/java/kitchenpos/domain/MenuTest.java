package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.common.Price;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MenuTest {

    @Test
    void 이미_다른_메뉴에_등록된_메뉴_상품을_넣으면_예외() {
        // given
        Menu anotherMenu = new Menu(
            2L,
            "nam2",
            new Price(BigDecimal.valueOf(0)),
            null,
            new ArrayList<>());

        List<MenuProduct> newMenuProducts = List.of(new MenuProduct(
            1L,
            new Product(1L, "product", new Price(BigDecimal.valueOf(1000))),
            anotherMenu,
            1L));

        // when && then
        Assertions.assertThatThrownBy(
                () -> new Menu(1L, "name", new Price(BigDecimal.valueOf(1000)), null, newMenuProducts))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 메뉴가 할당된 상품입니다.");
    }

}
