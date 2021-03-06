package com.procurement.mdm.repository

import com.procurement.mdm.model.entity.UnitClass
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UnitClassRepository : JpaRepository<UnitClass, String> {

    @Transactional(readOnly = true)
    fun findByUnitClassKeyLanguageCode(languageCode: String): List<UnitClass>

    @Transactional(readOnly = true)
    fun findByUnitClassKeyCodeAndUnitClassKeyLanguageCode(code: String, languageCode: String): UnitClass?
}
