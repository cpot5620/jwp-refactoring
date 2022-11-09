package kitchenpos.ordertable.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kitchenpos.ordertable.domain.infra.JdbcTemplateOrderTableDao;
import kitchenpos.tablegroup.domain.TableGroup;
import kitchenpos.tablegroup.domain.TableGroupDao;
import kitchenpos.tablegroup.domain.infra.JdbcTemplateTableGroupDao;
import org.springframework.stereotype.Repository;

@Repository
public class TableGroupRepository implements TableGroupDao {

    private final JdbcTemplateTableGroupDao jdbcTemplateTableGroupDao;
    private final JdbcTemplateOrderTableDao jdbcTemplateOrderTableDao;

    public TableGroupRepository(JdbcTemplateTableGroupDao jdbcTemplateTableGroupDao,
                                JdbcTemplateOrderTableDao jdbcTemplateOrderTableDao) {
        this.jdbcTemplateTableGroupDao = jdbcTemplateTableGroupDao;
        this.jdbcTemplateOrderTableDao = jdbcTemplateOrderTableDao;
    }

    @Override
    public TableGroup save(TableGroup entity) {
        TableGroup tableGroup = jdbcTemplateTableGroupDao.save(entity);

        List<OrderTable> savedOrderTables = saveOrderTables(tableGroup, entity.getOrderTables());
        tableGroup.setOrderTables(savedOrderTables);

        return tableGroup;
    }

    private List<OrderTable> saveOrderTables(TableGroup tableGroup, List<OrderTable> savedOrderTables) {
        List<OrderTable> orderTables = new ArrayList<>();

        for (OrderTable savedOrderTable : savedOrderTables) {
            OrderTable orderTable = new OrderTable(savedOrderTable.getId(), tableGroup.getId(),
                    savedOrderTable.getNumberOfGuests(), false);
            OrderTable table = jdbcTemplateOrderTableDao.save(orderTable);
            orderTables.add(table);
        }

        return orderTables;
    }

    @Override
    public Optional<TableGroup> findById(Long id) {
        return jdbcTemplateTableGroupDao.findById(id);
    }

    @Override
    public List<TableGroup> findAll() {
        return jdbcTemplateTableGroupDao.findAll();
    }
}
