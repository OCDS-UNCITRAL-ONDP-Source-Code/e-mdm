package com.procurement.mdm.domain.repository.criteria

import com.procurement.mdm.domain.entity.CriteriaEntity
import com.procurement.mdm.domain.model.Phase
import com.procurement.mdm.domain.model.Pmd
import com.procurement.mdm.domain.model.code.CountryCode
import com.procurement.mdm.domain.model.code.LanguageCode

interface CriteriaRepository {
    fun findBy(country: CountryCode, pmd: Pmd, language: LanguageCode, phase: Phase): List<CriteriaEntity>
}
