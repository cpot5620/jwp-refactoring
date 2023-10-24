package kitchenpos.dao;

import kitchenpos.domain.menu.Menu;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MenuDao extends Repository<Menu, Long> {
    Menu save(Menu entity);

    Optional<Menu> findById(Long id);

    List<Menu> findAll();

    long countByIdIn(List<Long> ids);
}
