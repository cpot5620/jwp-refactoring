package kitchenpos.ui.dto.menu;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.vo.Price;

import java.math.BigDecimal;
import java.util.List;

public class MenuRequest {

    private final String name;
    private final BigDecimal price;
    private final Long menuGroupId;
    private final List<MenuProductDto> menuProducts;

    public MenuRequest(
            final String name,
            final BigDecimal price,
            final Long menuGroupId,
            final List<MenuProductDto> menuProducts
    ) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public Menu toEntity(final MenuGroup menuGroup, final List<MenuProduct> menuProducts) {
        return Menu.of(name, new Price(price), menuGroup, menuProducts);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }
}
