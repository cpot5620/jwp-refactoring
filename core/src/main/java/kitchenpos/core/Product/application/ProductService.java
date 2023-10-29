package kitchenpos.core.Product.application;

import kitchenpos.core.Product.domain.Product;
import kitchenpos.core.Product.presentation.dto.ProductCreateRequest;
import kitchenpos.core.Product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(final ProductCreateRequest request) {
        final Product product = new Product(request.getName(), request.getPrice());
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
