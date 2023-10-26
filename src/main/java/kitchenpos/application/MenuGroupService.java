package kitchenpos.application;

import java.util.List;
import kitchenpos.domain.MenuGroup;
import kitchenpos.dto.menu.MenuGroupCreateRequest;
import kitchenpos.dto.menu.MenuGroupResponse;
import kitchenpos.mapper.MenuGroupMapper;
import kitchenpos.repository.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroupResponse create(final MenuGroupCreateRequest request) {
        final MenuGroup menuGroup = MenuGroupMapper.toMenuGroup(request);
        final MenuGroup savedMenuGroup = menuGroupRepository.save(menuGroup);

        return MenuGroupMapper.toMenuGroupResponse(savedMenuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> readAll() {
        final List<MenuGroup> menuGroups = menuGroupRepository.findAll();

        return MenuGroupMapper.toMenuGroupResponses(menuGroups);
    }
}
