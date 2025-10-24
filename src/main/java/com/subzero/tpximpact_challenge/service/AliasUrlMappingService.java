package com.subzero.tpximpact_challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.repository.AliasUrlMappingRepository;

public class AliasUrlMappingService {
    @Autowired
    private AliasUrlMappingRepository urlMappingRepository;

    AliasUrlMappingService(AliasUrlMappingRepository urlMappingRepository){
        this.urlMappingRepository = urlMappingRepository;
    }

    public AliasWithUrlMapping saveAliasWithUrlMappingToRepo(AliasWithUrlMapping aliasWithUrlMapping) {
        return urlMappingRepository.save(aliasWithUrlMapping);
    }

    public List<AliasWithUrlMapping> findAllAliasWithUrlMappingRecordsInRepo(){
        return urlMappingRepository.findAll();
    }

}
