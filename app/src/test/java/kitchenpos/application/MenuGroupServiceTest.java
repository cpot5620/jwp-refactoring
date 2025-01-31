package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.menugroup.application.MenuGroupService;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.menugroup.dto.request.MenuGroupResponse;
import kitchenpos.menugroup.dto.response.MenuGroupCreationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@Sql(value = "/initialization.sql")
class MenuGroupServiceTest {

    @Autowired
    private MenuGroupService menuGroupService;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @DisplayName("메뉴 그룹을 저장할 수 있다.")
    @Test
    void createSuccessTest() {
        //given
        MenuGroupCreationRequest request = new MenuGroupCreationRequest("TestMenuGroup");

        //when
        MenuGroupResponse response = menuGroupService.create(request);

        //then
        MenuGroup findMenuGroup = menuGroupRepository.findById(response.getId()).get();
        MenuGroupResponse expected = MenuGroupResponse.from(findMenuGroup);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("메뉴 그릅을 조회할 수 있다.")
    @Test
    void listSuccessTest() {
        //given
        MenuGroup menuGroup1 = MenuGroup.create("TestMenuGroup1");
        MenuGroup menuGroup2 = MenuGroup.create("TestMenuGroup2");

        menuGroupRepository.save(menuGroup1);
        menuGroupRepository.save(menuGroup2);

        //when
        List<MenuGroupResponse> responses = menuGroupService.list();

        //then
        List<MenuGroupResponse> expected = List.of(
                MenuGroupResponse.from(menuGroup1),
                MenuGroupResponse.from(menuGroup2)
        );

        Assertions.assertThat(responses).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

}
