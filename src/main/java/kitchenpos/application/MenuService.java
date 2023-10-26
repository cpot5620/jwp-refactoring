package kitchenpos.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;
import kitchenpos.dto.menu.MenuCreateRequest;
import kitchenpos.dto.menu.MenuProductRequest;
import kitchenpos.repository.MenuGroupRepository;
import kitchenpos.repository.MenuRepository;
import kitchenpos.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final BigDecimal price = request.getPrice();

        final MenuGroup menuGroup = findMenuGroupBy(request.getMenuGroupId());

        final List<MenuProduct> menuProducts = getMenuProducts(request.getMenuProducts());
        final Menu menu = Menu.of(menuGroup, menuProducts, request.getName(), price);
        return menuRepository.save(menu);
    }

    private MenuGroup findMenuGroupBy(final Long menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 메뉴 그룹이 존재하지 않습니다."));
    }

    private List<MenuProduct> getMenuProducts(
            final List<MenuProductRequest> menuProductRequests
    ) {
        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (final MenuProductRequest menuProductRequest : menuProductRequests) {
            final Product product = findProductBy(menuProductRequest.getProductId());
            final MenuProduct menuProduct = new MenuProduct(null, product, menuProductRequest.getQuantity());

            menuProducts.add(menuProduct);
        }
        return menuProducts;
    }

    private Product findProductBy(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 존재하지 않습니다."));
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
