package com.subzero.tpximpact_challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AliasUrlMappingRepositoryTest {

    @Autowired
    private AliasUrlMappingRepository aliasUrlMappingRepository;

    @Test
    void testSaveAndFindAll() {
        AliasWithUrlMapping aliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();
        aliasUrlMappingRepository.save(aliasWithUrlMapping);

        AliasWithUrlMapping aliasWithUrlMappingSecond = MockAliasUrlMappingBuilder.getCustomStubbedAliasWithUrlMappingForTesting("some-testing-alias", "https://www.example.test.com/long/url", "http://localhost:8080/some-testing-alias");
        aliasUrlMappingRepository.save(aliasWithUrlMappingSecond);

        List<AliasWithUrlMapping> urlMappingsReturned = aliasUrlMappingRepository.findAll();

        // I did this using @EqualsAndHashcode which generates a proper equals method for comparing all values.
        // Caveat is this is a "power of n", procedure thus as the number of items to compare becomes larger this would be less advisable an approach.
        assertEquals(urlMappingsReturned.get(0), aliasWithUrlMapping); 
        assertEquals(urlMappingsReturned.get(1), aliasWithUrlMappingSecond); 
        assertEquals(urlMappingsReturned.size(), 2);
    }
}