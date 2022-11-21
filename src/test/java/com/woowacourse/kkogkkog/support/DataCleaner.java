package com.woowacourse.kkogkkog.support;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataCleaner {

    private static final String TRUNCATE_FORMAT = "TRUNCATE TABLE %s";
    private static final String ID_RESET_FORMAT = "ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1";
    private static final String REFERENTIAL_FORMAT = "SET REFERENTIAL_INTEGRITY %s";

    private final EntityManager entityManager;
    private final DataSource dataSource;
    private final List<String> tableNames;

    @Transactional
    public void execute() {
        entityManager.clear();
        executeTruncate();
    }

    private void executeTruncate() {
        entityManager.flush();
        entityManager.createNativeQuery(String.format(REFERENTIAL_FORMAT, "FALSE")).executeUpdate();
        tableNames.forEach(tableName -> {
            entityManager.createNativeQuery(String.format(TRUNCATE_FORMAT, tableName)).executeUpdate();
            entityManager.createNativeQuery(String.format(ID_RESET_FORMAT, tableName)).executeUpdate();
        });
        entityManager.createNativeQuery(String.format(REFERENTIAL_FORMAT, "TRUE")).executeUpdate();
    }

    /**
     * Spring Boot 2.7.5 부터는 H2 DB의 기본 데이터베이스 테이블 값도 같이 가져옴
     */
    @PostConstruct
    public void afterPropertiesSet() {
        try (final var connection = dataSource.getConnection()) {
            final var metaData = connection.getMetaData();
            final var rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
