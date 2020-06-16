package com.procurement.mdm.application.service.criteria

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.procurement.mdm.domain.entity.CriteriaEntity
import com.procurement.mdm.domain.model.Phase
import com.procurement.mdm.domain.model.Pmd
import com.procurement.mdm.domain.model.code.CountryCode
import com.procurement.mdm.domain.model.code.LanguageCode
import com.procurement.mdm.domain.model.identifier.CriteriaIdentifier
import com.procurement.mdm.domain.repository.criteria.CriteriaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CriteriaServiceTest {

    companion object {
        private const val LANGUAGE = "en"
        private val LANGUAGE_CODE = LanguageCode(LANGUAGE)

        private const val COUNTRY = "md"
        private val COUNTRY_CODE = CountryCode(COUNTRY)

        private const val PMD = "ot"
        private val CRITERIA_PMD = Pmd(PMD)

        private const val PHASE = "submission"
        private val CRITERIA_PHASE = Phase(PHASE)

        private val FIRST_CRITERIA_ENTITY = CriteriaEntity(
            id = "MD_OT_1",
            description = "criteria-description-1",
            title = "criteria-title-1"
        )

        private val SECOND_CRITERIA_ENTITY = CriteriaEntity(
            id = "MD_OT_2",
            description = "criteria-description-2",
            title = "criteria-title-2"
        )
    }

    private lateinit var criteriaRepository: CriteriaRepository
    private lateinit var service: CriteriaService

    @BeforeEach
    fun init() {
        criteriaRepository = mock()
        service = mock()


        service = CriteriaServiceImpl(criteriaRepository)
    }

    @Test
    fun `Getting all criterion is successful`() {
        val storedCriterion = listOf(FIRST_CRITERIA_ENTITY, SECOND_CRITERIA_ENTITY)
        whenever(
            criteriaRepository.findBy(
                country = eq(COUNTRY_CODE),
                pmd = eq(CRITERIA_PMD),
                language = eq(LANGUAGE_CODE),
                phase = eq(CRITERIA_PHASE)
            )
        )
            .thenReturn(storedCriterion)

        val actual = service.getAll(country = COUNTRY, phase = PHASE, pmd = PMD, language = LANGUAGE)

        val expected = storedCriterion.map { entity ->
            CriteriaIdentifier(id = entity.id, title = entity.title, description = entity.description)
        }

        assertEquals(expected, actual)
    }

    @Test
    fun `Getting all criterion fails (criteria not found)`() {
        whenever(
            criteriaRepository.findBy(
                country = eq(COUNTRY_CODE),
                pmd = eq(CRITERIA_PMD),
                language = eq(LANGUAGE_CODE),
                phase = eq(CRITERIA_PHASE)
            )
        )
            .thenReturn(emptyList())

        val actual = service.getAll(country = COUNTRY, phase = PHASE, pmd = PMD, language = LANGUAGE)

        assertTrue(actual.isEmpty())
    }

}
