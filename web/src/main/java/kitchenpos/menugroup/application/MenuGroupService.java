package kitchenpos.menugroup.application;

import java.util.List;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.dto.request.MenuGroupCreateRequest;
import kitchenpos.menugroup.dto.response.MenuGroupResponse;
import kitchenpos.menugroup.repository.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroupResponse create(final MenuGroupCreateRequest request) {
        MenuGroup menuGroup = new MenuGroup(null, request.getMenuGroupName());
        return MenuGroupResponse.of(menuGroupRepository.save(menuGroup));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> list() {
        List<MenuGroup> allMenuGroup = menuGroupRepository.findAll();
        return MenuGroupResponse.of(allMenuGroup);
    }
}
