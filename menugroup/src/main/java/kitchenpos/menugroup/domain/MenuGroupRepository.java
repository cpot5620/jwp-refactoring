package kitchenpos.menugroup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {

    boolean existsById(Long id);

}
