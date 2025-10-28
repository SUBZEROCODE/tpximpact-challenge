package com.subzero.tpximpact_challenge.containers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.subzero.tpximpact_challenge.ContainerAppConfig;
import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.repository.AliasUrlMappingRepository;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

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
    }

    @Test
    void testPostgresContainerProperties() {
        try  {
            postgresContainerisedDb.start();
            postgresContainerisedDb.waitingFor(Wait.forListeningPort());
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

    @Test
    void testGetMostPopularFullUrlFromWithinPostgresDBEnvironment() {
    try {
        // Wait for postgres to be healthy for use
        postgresContainerisedDb.waitingFor(Wait.forListeningPort());
        AliasWithUrlMapping aliasWithUrlMappingTest = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();
        String localHostUrl = "http://localhost:8080/";
        String popularTestUrl = "http://www.example.test.com/some/popular/url";
        AliasWithUrlMapping aliasWithUrlMappingWithSameTestFullUrl = MockAliasUrlMappingBuilder.getCustomStubbedAliasWithUrlMappingForTesting(
            "popular-test-alias", "http://www.example.test.com/some/popular/url", localHostUrl + "popular-test-alias"
        );
        AliasWithUrlMapping aliasWithUrlMappingWithSameTestFullUrl2 = MockAliasUrlMappingBuilder.getCustomStubbedAliasWithUrlMappingForTesting(
            "popular-test-alias2", "http://www.example.test.com/some/popular/url", localHostUrl + "popular-test-alias2"
        );

        aliasUrlMappingRepository.save(aliasWithUrlMappingTest);
        aliasUrlMappingRepository.save(aliasWithUrlMappingWithSameTestFullUrl);
        aliasUrlMappingRepository.save(aliasWithUrlMappingWithSameTestFullUrl2);

        String mostPopularFullUrl = aliasUrlMappingService.getMostPopularFullUrl();
        assertEquals(popularTestUrl, mostPopularFullUrl);
    } catch (Exception e) {
        fail("Exception during database connection test: " + e.getMessage());
    } finally {
        postgresContainerisedDb.stop();
    }
    }
}
