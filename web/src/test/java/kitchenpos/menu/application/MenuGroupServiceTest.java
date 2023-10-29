package kitchenpos.menu.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.config.ServiceTest;
import kitchenpos.menugroup.application.MenuGroupService;
import kitchenpos.menugroup.application.request.MenuGroupCreateRequest;
import kitchenpos.menugroup.application.response.MenuGroupResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
@ServiceTest
class MenuGroupServiceTest {

    @Autowired
    private MenuGroupService menuGroupService;

    @Test
    void 메뉴_그룹을_생성한다() {
        MenuGroupCreateRequest menuGroupCreateRequest = new MenuGroupCreateRequest("단품");

        MenuGroupResponse response = menuGroupService.create(menuGroupCreateRequest);

        assertThat(response.getName()).isEqualTo(menuGroupCreateRequest.getName());
    }

    @Test
    void 모든_메뉴_그룹을_조회한다() {
        MenuGroupCreateRequest menuGroupCreateRequest = new MenuGroupCreateRequest("단품");
        menuGroupService.create(menuGroupCreateRequest);

        List<MenuGroupResponse> menuGroupResponses = menuGroupService.list();

        assertThat(menuGroupResponses).hasSizeGreaterThan(1);
    }
}
