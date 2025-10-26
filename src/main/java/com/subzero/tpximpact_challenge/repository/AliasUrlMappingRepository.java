package com.subzero.tpximpact_challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;

public interface AliasUrlMappingRepository extends JpaRepository<AliasWithUrlMapping, String> {}