package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Price price;

    private Long menuGroupId;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public Menu() {
    }

    public Menu(Long id, String name, Price price, Long menuGroupId, List<MenuProduct> menuProducts) {
        validateAdditionalPrice(menuProducts, price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        addMenuProducts(menuProducts);
    }

    private void validateAdditionalPrice(List<MenuProduct> menuProducts, Price requestPrice) {
        Price totalPrice = new Price(BigDecimal.valueOf(0));
        for (MenuProduct menuProduct : menuProducts) {
            totalPrice = totalPrice.plus(menuProduct.price());
        }
        if (requestPrice.compareTo(totalPrice) > 0) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품들의 총합보다 비쌀 수 없습니다.");
        }
    }

    private void addMenuProducts(List<MenuProduct> menuProducts) {
        for (MenuProduct menuProduct : menuProducts) {
            menuProduct.assignMenu(this);
            this.menuProducts.add(menuProduct);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
