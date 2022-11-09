package kitchenpos.menu.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuDao;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menugroup.domain.MenuGroupDao;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MenuService {
    private final MenuDao menuDao;
    private final MenuGroupDao menuGroupDao;
    private final ProductDao productDao;

    public MenuService(MenuDao menuDao, MenuGroupDao menuGroupDao, ProductDao productDao) {
        this.menuDao = menuDao;
        this.menuGroupDao = menuGroupDao;
        this.productDao = productDao;
    }

    public MenuResponse create(Menu request) {
        if (!menuGroupDao.existsById(request.getMenuGroupId())) {
            throw new IllegalArgumentException("메뉴 집합이 존재하지 않습니다.");
        }

        List<MenuProduct> menuProducts = findMenuProduct(request.getMenuProducts());
        Menu menu = Menu.of(request.getName(), request.getPrice(), request.getMenuGroupId(), menuProducts);

        return new MenuResponse(menuDao.save(menu));
    }

    private List<MenuProduct> findMenuProduct(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(menuProduct -> {
                    Product product = findProduct(menuProduct.getProductId());
                    return MenuProduct.of(product.getId(), menuProduct.getQuantity(), product.getPrice());
                }).collect(Collectors.toList());
    }

    private Product findProduct(Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> list() {
        return menuDao.findAll()
                .stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }
}
