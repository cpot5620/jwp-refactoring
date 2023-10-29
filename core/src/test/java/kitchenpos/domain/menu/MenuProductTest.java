package kitchenpos.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.domain.common.Price;
import kitchenpos.domain.exception.InvalidQuantityException;
import kitchenpos.domain.menugroup.MenuGroup;
import kitchenpos.domain.product.Product;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuProductTest {

    @Test
    void 생성자는_유효한_product와_quantity를_전달하면_예외가_발생한다() {
        // given
        final Product product = new Product("상품", BigDecimal.TEN);

        // when & then
        assertThatCode(() -> new MenuProduct(product.getId(), 1L)).doesNotThrowAnyException();
    }

    @Test
    void 생성자는_유효하지_않은_quantity를_전달하면_예외가_발생한다() {
        // given
        final Product product = new Product("상품", BigDecimal.TEN);
        final long invalidQuantity = -999L;

        // when & then
        assertThatThrownBy(() -> new MenuProduct(product.getId(), invalidQuantity))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void initMenu_메서드는_menu를_전달하면_menu_필드를_초기화한다() {
        // given
        final Product product = new Product("상품", BigDecimal.TEN);
        final MenuGroup menuGroup = new MenuGroup("메뉴 그룹");
        final MenuProduct menuProduct = new MenuProduct(product.getId(), 1L);
        final Price price = new Price(BigDecimal.TEN);
        final Menu menu = new Menu(
                "메뉴",
                BigDecimal.TEN,
                menuGroup.getId(),
                MenuProducts.of(price, price, List.of(menuProduct))
        );
        final MenuProduct targetMenuProduct = new MenuProduct(product.getId(), 2L);

        // when
        targetMenuProduct.initMenu(menu);

        // then
        assertThat(targetMenuProduct.getMenu()).isEqualTo(menu);
    }
}
