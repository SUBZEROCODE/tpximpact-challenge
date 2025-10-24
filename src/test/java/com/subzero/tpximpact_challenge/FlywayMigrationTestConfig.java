
package com.subzero.tpximpact_challenge;
import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FlywayMigrationTestConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "")
                .locations("classpath:db/migration")
                .load();
    }
}