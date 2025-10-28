package com.subzero.tpximpact_challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;

public interface AliasUrlMappingRepository extends JpaRepository<AliasWithUrlMapping, String> {

    @Query(value = "SELECT full_url FROM alias_with_url_mapping GROUP BY full_url ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Optional<String> getMostPopularFullUrl();
    
}