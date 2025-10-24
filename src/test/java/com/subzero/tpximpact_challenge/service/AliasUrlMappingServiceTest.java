package com.subzero.tpximpact_challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

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
    private AliasUrlMappingRepository mockUrlMappingRepository;

    @InjectMocks
    private AliasUrlMappingService aliasWithUrlMappingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testsaveAliasWithUrlMappingToRepoShouldCallSaveMethodOnceAndReturnSavedObject() {
        AliasWithUrlMapping aliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        when(mockUrlMappingRepository.save(any(AliasWithUrlMapping.class))).thenReturn(aliasWithUrlMapping);

        AliasWithUrlMapping returnedAliasWithUrlMapping = aliasWithUrlMappingService.saveAliasWithUrlMappingToRepo(aliasWithUrlMapping);

        assertEquals(returnedAliasWithUrlMapping, aliasWithUrlMapping);
        verify(mockUrlMappingRepository, times(1)).save(any(AliasWithUrlMapping.class));
    }

    @Test
    void findAllAliasWithUrlMappingRecordsInRepoShouldCallUrlMappingRepoAndFindAll() {
        List<AliasWithUrlMapping> emptyList = Collections.emptyList();

        when(mockUrlMappingRepository.findAll()).thenReturn(emptyList);

        aliasWithUrlMappingService.findAllAliasWithUrlMappingRecordsInRepo();
        verify(mockUrlMappingRepository, times(1)).findAll();
    }
    
}
