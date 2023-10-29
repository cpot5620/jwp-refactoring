package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.product.apllication.ProductService;
import kitchenpos.product.domain.Product;
import kitchenpos.product.dto.request.ProductCreateRequest;
import kitchenpos.product.dto.response.ProductResponse;
import kitchenpos.product.repository.ProductRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void 목록_조회() {
        // given
        List<Product> products = List.of(ProductFixture.builder().withName("상품이름").withPrice(1000L).build());
        given((productRepository.findAll()))
            .willReturn(products);

        // when
        List<ProductResponse> actual = productService.list();

        // then
        assertThat(actual).hasSize(1);
    }

    @Nested
    class 생성_테스트 {

        @Test
        void 상품의_가격이_0_보다_작으면_예외() {
            // given
            ProductCreateRequest request = new ProductCreateRequest("name", BigDecimal.valueOf(-1));

            // when && then
            assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품의_가격을_명시하지않으면_예외() {
            // given
            ProductCreateRequest request = new ProductCreateRequest("name", null);

            // when && then
            assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품의_이름을_명시하지_않으면_예외() {
            // given
            ProductCreateRequest request = new ProductCreateRequest(null, BigDecimal.valueOf(1000));

            // when && then
            assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름은 필수입니다.");
        }

        @Test
        void 생성_성공() {
            // given
            String productName = "product";
            long productPrice = 1000L;
            ProductCreateRequest request = new ProductCreateRequest(productName,
                BigDecimal.valueOf(productPrice));

            Product product = ProductFixture.builder()
                .withName(productName)
                .withPrice(productPrice)
                .build();
            given(productRepository.save(any()))
                .willReturn(product);

            // when
            ProductResponse actual = productService.create(request);

            // then
            SoftAssertions.assertSoftly(softAssertions -> {
                assertThat(actual.getName()).isEqualTo(productName);
                assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(productPrice));
            });
        }
    }
}
