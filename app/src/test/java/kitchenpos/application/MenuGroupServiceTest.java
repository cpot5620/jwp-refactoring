package kitchenpos.application;

import kitchenpos.menu.application.dto.request.MenuGroupCreateRequest;
import kitchenpos.menu.application.dto.response.MenuGroupResponse;
import kitchenpos.menu.application.MenuGroupService;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.persistence.MenuGroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    @Test
    void 메뉴_그룹을_생성한다() {
        // given
        final MenuGroup savedMenuGroup = new MenuGroup(1L, "신메뉴");
        when(menuGroupRepository.save(any(MenuGroup.class)))
                .thenReturn(savedMenuGroup);

        // when
        final MenuGroupResponse result = menuGroupService.create(new MenuGroupCreateRequest("신메뉴"));
        final MenuGroupResponse expect = new MenuGroupResponse(1L, "신메뉴");

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }

    @Test
    void 모든_메뉴_그룹을_반환한다() {
        // given
        when(menuGroupRepository.findAll())
                .thenReturn(Collections.emptyList());

        // when
        final List<MenuGroupResponse> result = menuGroupService.list();

        // then
        assertThat(result).isEmpty();
    }
}