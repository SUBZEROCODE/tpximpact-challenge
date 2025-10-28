package com.subzero.tpximpact_challenge.containers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.repository.AliasUrlMappingRepository;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

@SpringBootTest
@Testcontainers
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

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("db.url", postgresContainerisedDb::getJdbcUrl);
        registry.add("db.username", postgresContainerisedDb::getUsername);
        registry.add("db.password", postgresContainerisedDb::getPassword);
        registry.add("db.driver", postgresContainerisedDb::getDriverClassName);
        registry.add("spring.flyway.enabled", () -> false);
    }

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        aliasUrlMappingService = new AliasUrlMappingService(aliasUrlMappingRepository);
    }

    @Test
    void testPostgresContainerPropertiesOnceStarted() {
        try  {
            postgresContainerisedDb.start();
            postgresContainerisedDb.waitingFor(Wait.forListeningPort());
            assertEquals("container-test", postgresContainerisedDb.getUsername());
            assertEquals("container-pass", postgresContainerisedDb.getPassword());
            assertEquals("java-test-db", postgresContainerisedDb.getDatabaseName()); // default db name
        } finally {
            postgresContainerisedDb.stop();
        }
    }

    @Test
    @Sql("/db/migration/V1__create_table_alias_url_mappings.sql")
    void testGetMostPopularFullUrlFromWithinPostgresDBEnvironment() {
    try {
        // Wait for postgres to be healthy for use
        postgresContainerisedDb.waitingFor(Wait.forListeningPort());
        String expectedContainerisedJdbcUrl = postgresContainerisedDb.getJdbcUrl();
        String actualJdbcUrlFromDatasource = dataSource.getConnection().getMetaData().getURL();

        assertTrue(actualJdbcUrlFromDatasource.contains(expectedContainerisedJdbcUrl));

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
