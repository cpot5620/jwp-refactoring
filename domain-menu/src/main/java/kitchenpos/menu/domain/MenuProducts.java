package kitchenpos.menu.domain;

import kitchenpos.common.vo.Price;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class MenuProducts {

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "menu_id", nullable = false)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {}

    public MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public Price calculateTotalPrice() {
        final Price totalPrice = Price.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            totalPrice.sum(menuProduct.calculatePrice());
        }

        return totalPrice;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
