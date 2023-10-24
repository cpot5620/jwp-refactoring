package kitchenpos.dao;

import kitchenpos.domain.table.TableGroup;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TableGroupDao extends Repository<TableGroup, Long> {
    TableGroup save(TableGroup entity);

    Optional<TableGroup> findById(Long id);

    List<TableGroup> findAll();
}
