package com.subzero.tpximpact_challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.subzero.tpximpact_challenge.ContainerAppConfig;
import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.repository.AliasUrlMappingRepository;

@Service
public class AliasUrlMappingService {
    @Autowired
    private AliasUrlMappingRepository urlMappingRepository;

    @Autowired
    ContainerAppConfig appConfig;

    public AliasUrlMappingService(AliasUrlMappingRepository urlMappingRepository){
        this.urlMappingRepository = urlMappingRepository;
    }

    public AliasWithUrlMapping saveAliasWithUrlMappingToRepo(AliasWithUrlMapping aliasWithUrlMapping) {
        return urlMappingRepository.save(aliasWithUrlMapping);
    }

    public Optional<AliasWithUrlMapping> findAliasBasedOnParameterAliasPassedIn(String alias){
        return urlMappingRepository.findById(alias);
    }

    public List<AliasWithUrlMapping> findAllAliasWithUrlMappingRecordsInRepo(){
        return urlMappingRepository.findAll();
    }

    public void deleteByAliasFoundInRepo(AliasWithUrlMapping aliasUrlMappingToRemove){

        try {
            urlMappingRepository.delete(aliasUrlMappingToRemove);
        } catch (EmptyResultDataAccessException e) {
            // Thrown if the entity doesn't exist
            System.err.println("No entity found to delete: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            // Thrown if deletion violates a constraint (e.g. foreign key)
            System.err.println("Constraint violation during delete: " + e.getMessage());
        } catch (JpaObjectRetrievalFailureException e) {
            // Thrown if the entity is not managed or can't be retrieved
            System.err.println("Entity retrieval failure: " + e.getMessage());
        } catch (Exception e) {
            // Fallback for unexpected issues
            System.err.println("Unexpected error during delete: " + e.getMessage());
        }
    }

    public String getMostPopularFullUrl(){
        Optional<String> mostPopularUrlOptional = urlMappingRepository.getMostPopularFullUrl();

        if(mostPopularUrlOptional.isPresent()){
            return mostPopularUrlOptional.get();
        } else {
            return "NO POPULAR RECORD FOUND";
        }
    }

}
