package com.subzero.tpximpact_challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

@DataJpaTest
@ActiveProfiles(value = "test")
class AliasUrlMappingRepositoryTest {

    @Autowired
    private AliasUrlMappingRepository aliasUrlMappingRepository;

    @Test
    void testSaveAndFindAllShouldReturnAllSavedMappings() {
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

    @Test 
    void saveTwoUrlMappingsAndDeleteShouldOnlyReturnOneAfterDeletion() {
        AliasWithUrlMapping aliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();
        aliasUrlMappingRepository.save(aliasWithUrlMapping);

        AliasWithUrlMapping aliasWithUrlMappingSecond = MockAliasUrlMappingBuilder.getCustomStubbedAliasWithUrlMappingForTesting("some-testing-alias", "https://www.example.test.com/long/url", "http://localhost:8080/some-testing-alias");
        aliasUrlMappingRepository.save(aliasWithUrlMappingSecond);

        List<AliasWithUrlMapping> urlMappingsReturned = aliasUrlMappingRepository.findAll();
        assertEquals(urlMappingsReturned.size(), 2);

        aliasUrlMappingRepository.delete(aliasWithUrlMapping);

        List<AliasWithUrlMapping> urlMappingsReturnedAfterDelete = aliasUrlMappingRepository.findAll();
        assertEquals(urlMappingsReturnedAfterDelete.get(0), aliasWithUrlMappingSecond); 
        assertEquals(urlMappingsReturnedAfterDelete.size(), 1);
    }

    @Test
    void getMostPopularFullUrlShouldReturnMostPopularUrlWhenOneExists(){
        AliasWithUrlMapping aliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();
        aliasUrlMappingRepository.save(aliasWithUrlMapping);

        String popularTestUrl = "http://www.example.test.com/some/popular/url";
        AliasWithUrlMapping aliasWithUrlMappingPopular = MockAliasUrlMappingBuilder.getCustomStubbedAliasWithUrlMappingForTesting("some-testing-alias", popularTestUrl, "http://localhost:8080/some-testing-alias");
        aliasUrlMappingRepository.save(aliasWithUrlMappingPopular);

        AliasWithUrlMapping aliasWithUrlMappingPopular2 = MockAliasUrlMappingBuilder.getCustomStubbedAliasWithUrlMappingForTesting("some-testing-alias", popularTestUrl, "http://localhost:8080/some-testing-alias");
        aliasUrlMappingRepository.save(aliasWithUrlMappingPopular2);

        Optional<String> mostPopularFullUrl = aliasUrlMappingRepository.getMostPopularFullUrl();
        assertEquals(popularTestUrl, mostPopularFullUrl.get());
    }

    @Test
    void getMostPopularFullUrlShouldReturnEmptyOptionalWhenNoRecordsHaveBeenSaved(){
        Optional<String> mostPopularFullUrl = aliasUrlMappingRepository.getMostPopularFullUrl();
        assertEquals(false, mostPopularFullUrl.isPresent());
    }
}