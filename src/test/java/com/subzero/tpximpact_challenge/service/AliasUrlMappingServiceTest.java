package com.subzero.tpximpact_challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.repository.AliasUrlMappingRepository;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

public class AliasUrlMappingServiceTest {
    @Mock
    private AliasUrlMappingRepository repository;

    @InjectMocks
    private AliasUrlMappingService aliasWithUrlMappingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testsaveAliasWithUrlMappingToRepoShouldCallSaveMethodONceAndReturnSavedObject() {
        AliasWithUrlMapping aliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        when(repository.save(any(AliasWithUrlMapping.class))).thenReturn(aliasWithUrlMapping);

        AliasWithUrlMapping returnedAliasWithUrlMapping = aliasWithUrlMappingService.saveAliasWithUrlMappingToRepo(aliasWithUrlMapping);

        assertEquals(returnedAliasWithUrlMapping, aliasWithUrlMapping);
        verify(repository, times(1)).save(any(AliasWithUrlMapping.class));
    }
    
}
