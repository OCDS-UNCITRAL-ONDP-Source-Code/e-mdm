package com.procurement.mdm.repository

import com.procurement.mdm.model.entity.RegistrationScheme
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RegistrationSchemeRepository : JpaRepository<RegistrationScheme, String> {

    @Transactional(readOnly = true)
    fun findByCountryId(countryId: String): List<RegistrationScheme>
}
