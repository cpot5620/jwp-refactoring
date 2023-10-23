package kitchenpos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleanup implements InitializingBean {

    private static final String TRUNCATE_SQL_MESSAGE = "TRUNCATE TABLE %s";
    private static final String SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE = "SET FOREIGN_KEY_CHECKS %s";
    private static final String DISABLE_REFERENTIAL_QUERY = String.format(SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE, 0);
    private static final String ENABLE_REFERENTIAL_QUERY = String.format(SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE, 1);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final List<String> tableNames = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws SQLException {
        ResultSet rs = jdbcTemplate.getDataSource()
                .getConnection()
                .getMetaData()
                .getTables(null, "PUBLIC", null, new String[]{"TABLE"});

        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");

            this.tableNames.add(tableName);
        }
    }

    @Transactional
    public void execute() {
        disableReferentialIntegrity();
        executeTruncate();
        enableReferentialIntegrity();
    }

    private void disableReferentialIntegrity() {
        jdbcTemplate.execute(DISABLE_REFERENTIAL_QUERY);
    }

    private void executeTruncate() {
        for (String tableName : tableNames) {
            String TRUNCATE_QUERY = String.format(TRUNCATE_SQL_MESSAGE, tableName);

            executeWithException(TRUNCATE_QUERY);
        }
    }

    private void executeWithException(String TRUNCATE_QUERY) {
        try {
            jdbcTemplate.execute(TRUNCATE_QUERY);
        } catch (Exception e) {
            // ignored
        }
    }

    private void enableReferentialIntegrity() {
        jdbcTemplate.execute(ENABLE_REFERENTIAL_QUERY);
    }

}
