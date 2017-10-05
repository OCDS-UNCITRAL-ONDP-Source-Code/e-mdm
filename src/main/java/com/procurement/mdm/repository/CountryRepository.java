package com.procurement.mdm.repository;

import com.procurement.mdm.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findCountriesByCode(String code);

    List<Country> findCountriesByName(String name);
}
