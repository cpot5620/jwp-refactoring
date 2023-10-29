package kitchenpos.menu.ui;

import kitchenpos.menu.application.dto.request.MenuCreateRequest;
import kitchenpos.menu.application.dto.response.MenuResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/truncate_insert_data.sql")
class MenuRestControllerTest {

    @Autowired
    MenuRestController menuRestController;

    @DisplayName("POST {{host}}/api/menus")
    @Test
    void menu_create() {
        //given
        final MenuCreateRequest request = MenuRequestFixture.menuCreateRequest();
        //when
        final ResponseEntity<MenuResponse> response = menuRestController.create(request);
        //then
        SoftAssertions.assertSoftly(
                soft -> {
                    soft.assertThat(response.getBody().getId()).isNotNull();
                    soft.assertThat(response.getBody().getName()).isEqualTo(request.getName());
                    soft.assertThat(response.getBody().getMenuProducts().get(0).getSeq()).isNotNull();
                }
        );
    }

    @DisplayName("GET {{host}}/api/menus")
    @Test
    void menu_list() {
        //given

        //when
        final ResponseEntity<List<MenuResponse>> response = menuRestController.list();
        //then
        SoftAssertions.assertSoftly(
                soft -> {
                    soft.assertThat(response.getBody().size()).isEqualTo(6);
                }
        );
    }
}
