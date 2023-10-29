package kitchenpos.ui;

import static kitchenpos.fixture.MenuFixture.메뉴;
import static kitchenpos.fixture.MenuFixture.메뉴_등록_요청;
import static kitchenpos.fixture.MenuFixture.메뉴_등록_응답;
import static kitchenpos.fixture.MenuFixture.메뉴_상품;
import static kitchenpos.fixture.MenuFixture.메뉴_상품들;
import static kitchenpos.fixture.MenuGroupFixture.메뉴_그룹;
import static kitchenpos.fixture.ProductFixture.상품;
import static org.assertj.core.api.Assertions.assertThat;

import application.dto.MenuCreateRequest;
import application.dto.MenuResponse;
import domain.Menu;
import domain.MenuGroup;
import domain.MenuValidator;
import domain.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.math.BigDecimal;
import java.util.List;
import domain.menu_product.MenuProductValidator;
import domain.menu_product.MenuProducts;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import repository.MenuGroupRepository;
import repository.MenuRepository;
import repository.ProductRepository;

@SuppressWarnings("NonAsciiCharacters")
class MenuRestControllerTest extends ControllerTest {

    @Autowired
    private MenuGroupRepository menuGroupRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuProductValidator menuProductValidator;
    @Autowired
    private MenuValidator menuValidator;

    private String name;
    private BigDecimal price;
    private MenuGroup menuGroup;
    private MenuProducts menuProducts;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        this.product1 = productRepository.save(상품("상품1", BigDecimal.valueOf(1000)));
        this.product2 = productRepository.save(상품("상품2", BigDecimal.valueOf(1000)));
        this.menuProducts = 메뉴_상품들(메뉴_상품(
                        product1.getId(), 1L, menuProductValidator),
                메뉴_상품(product2.getId(), 2L, menuProductValidator)
        );
        this.menuGroup = menuGroupRepository.save(메뉴_그룹("메뉴_그룹"));
        this.name = "메뉴";
        this.price = BigDecimal.valueOf(3000);
    }

    @Test
    void 메뉴_등록시_이름이_없다면_예외가_발생한다() {
        // given
        final MenuCreateRequest request = 메뉴_등록_요청(null, price, menuGroup, menuProducts);
        RequestSpecification given = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request);

        // when
        final ExtractableResponse<Response> result = given
                .when().log().all()
                .post("/api/menus")
                .then().extract();

        // then
        assertThat(result.statusCode()).isEqualTo(500);
    }

    @Test
    void 메뉴_등록시_가격이_없다면_예외가_발생한다() {
        // given
        final MenuCreateRequest request = 메뉴_등록_요청(name, null, menuGroup, menuProducts);
        RequestSpecification given = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request);

        // when
        final ExtractableResponse<Response> result = given
                .when().log().all()
                .post("/api/menus")
                .then().extract();

        // then
        assertThat(result.statusCode()).isEqualTo(500);
    }

    @Test
    void 메뉴_등록시_가격이_메뉴_상품과_다르면_예외가_발생한다() {
        // given
        final MenuCreateRequest request = 메뉴_등록_요청(name, BigDecimal.valueOf(Long.MAX_VALUE), menuGroup, menuProducts);
        RequestSpecification given = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request);

        // when
        final ExtractableResponse<Response> result = given
                .when().log().all()
                .post("/api/menus")
                .then().extract();

        // then
        assertThat(result.statusCode()).isEqualTo(500);
    }

    @Test
    void 메뉴_등록시_메뉴_그룹이_없다면_예외가_발생한다() {
        // given
        final MenuCreateRequest request = 메뉴_등록_요청(name, price, Long.MAX_VALUE, menuProducts);
        RequestSpecification given = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request);

        // when
        final ExtractableResponse<Response> result = given
                .when().log().all()
                .post("/api/menus")
                .then().extract();

        // then
        assertThat(result.statusCode()).isEqualTo(500);
    }

    @Test
    void 메뉴_등록시_메뉴_상품이_없다면_예외가_발생한다() {
        // given
        final MenuCreateRequest request = new MenuCreateRequest(name, price, menuGroup.getId(), null);
        RequestSpecification given = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request);

        // when
        final ExtractableResponse<Response> result = given
                .when().log().all()
                .post("/api/menus")
                .then().extract();

        // then
        assertThat(result.statusCode()).isEqualTo(500);
    }

    @Test
    void 메뉴를_등록한다() {
        // given
        final MenuCreateRequest request = 메뉴_등록_요청(name, price, menuGroup, menuProducts);
        RequestSpecification given = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(request);

        // when
        final ExtractableResponse<Response> result = given
                .when().log().all()
                .post("/api/menus")
                .then().extract();
        final MenuResponse response = result.as(MenuResponse.class);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.statusCode()).isEqualTo(201);
            softly.assertThat(response.getId()).isNotNull();
            softly.assertThat(response)
                    .usingRecursiveComparison()
                    .ignoringFields("id", "menuProducts.seq")
                    .isEqualTo(메뉴_등록_응답(메뉴(name, price.longValue(), menuProducts, menuGroup, menuValidator)));
        });
    }

    @Test
    void 메뉴를_전체_조회한다() {
        // given
        final MenuProducts menuProducts2 = 메뉴_상품들(메뉴_상품(
                        product1.getId(), 1L, menuProductValidator),
                메뉴_상품(product2.getId(), 2L, menuProductValidator)
        );
        final Menu menu1 = menuRepository.save(메뉴("메뉴1", 3000L, menuProducts, menuGroup, menuValidator));
        final Menu menu2 = menuRepository.save(메뉴("메뉴2", 3000L, menuProducts2, menuGroup, menuValidator));

        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .when()
                .get("/api/menus")
                .then().log().all().extract();
        List<MenuResponse> response = result.jsonPath().getList(".", MenuResponse.class);

        // then
        SoftAssertions.assertSoftly(softly -> {
            assertThat(response.size()).isEqualTo(2);
            assertThat(response)
                    .usingRecursiveComparison()
                    .ignoringFields("price")
                    .isEqualTo(List.of(메뉴_등록_응답(menu1), 메뉴_등록_응답(menu2)));
        });
    }
}
