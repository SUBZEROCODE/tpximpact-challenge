package com.subzero.tpximpact_challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

@DataJpaTest
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

        assertEquals(urlMappingsReturned.size(), 2);
    }
}