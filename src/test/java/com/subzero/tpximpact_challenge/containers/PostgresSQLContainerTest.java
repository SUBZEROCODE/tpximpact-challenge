package com.subzero.tpximpact_challenge.containers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.subzero.tpximpact_challenge.ContainerAppConfig;
import com.subzero.tpximpact_challenge.repository.AliasUrlMappingRepository;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;

@DataJpaTest
@Testcontainers
@Import({AliasUrlMappingService.class, ContainerAppConfig.class})
public class PostgresSQLContainerTest {
    @Autowired
    private AliasUrlMappingRepository aliasUrlMappingRepository;

    @Autowired
    AliasUrlMappingService aliasUrlMappingService;

    // This can be supressed as Testcontainers with manage closing the postgres container after use
    @SuppressWarnings("resource")
    @Container
    static final PostgreSQLContainer<?> postgresContainerisedDb = new PostgreSQLContainer<>("postgres:16.1").withUsername("container-test")
            .withPassword("container-pass")
            .withDatabaseName("java-test-db");

    @Autowired
    private ContainerAppConfig containerAppConfig;

    @BeforeEach
    void setUp() {
        containerAppConfig = new ContainerAppConfig();
        containerAppConfig.setUrl(postgresContainerisedDb.getJdbcUrl());
        containerAppConfig.setUsername(postgresContainerisedDb.getUsername());
        containerAppConfig.setPassword(postgresContainerisedDb.getPassword());
        containerAppConfig.setName(postgresContainerisedDb.getDatabaseName());
        aliasUrlMappingService = new AliasUrlMappingService(aliasUrlMappingRepository );
        //aliasUrlMappingService.modifyAppConfigUsedToAnotherDatasource(appConfig);
    }

    @Test
    void testPostgresContainerProperties() {
        try  {
            postgresContainerisedDb.start();
            assertEquals("container-test", postgresContainerisedDb.getUsername());
            assertEquals("container-pass", postgresContainerisedDb.getPassword());

            // Java maps its internal port of 5432 to a mapped port for its JDBC usage.
            String jdbcUrlWithRandomMappedPort = String.format("jdbc:postgresql://localhost:%d/java-test-db?loggerLevel=OFF", postgresContainerisedDb.getMappedPort(5432));
            assertEquals(jdbcUrlWithRandomMappedPort, postgresContainerisedDb.getJdbcUrl());
            assertEquals("java-test-db", postgresContainerisedDb.getDatabaseName()); // default db name
        } finally {
            postgresContainerisedDb.stop();
        }
    }
}
