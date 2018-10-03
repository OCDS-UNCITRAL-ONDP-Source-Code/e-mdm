package com.procurement.mdm.service.preparation

import com.procurement.access.utils.toObject
import com.procurement.mdm.exception.ErrorType
import com.procurement.mdm.exception.InErrorException
import com.procurement.mdm.model.dto.CommandMessage
import com.procurement.mdm.model.dto.ResponseDto
import com.procurement.mdm.model.dto.data.ClassificationScheme
import com.procurement.mdm.model.dto.data.EI
import com.procurement.mdm.model.dto.data.FS
import com.procurement.mdm.model.dto.getResponseDto
import com.procurement.mdm.repository.CpvRepository
import com.procurement.mdm.repository.CurrencyRepository
import com.procurement.mdm.service.ValidationService
import org.springframework.stereotype.Service

interface BudgetDataService {

    fun processEiData(cm: CommandMessage): ResponseDto

    fun processFsData(cm: CommandMessage): ResponseDto
}

@Service
class BudgetDataServiceImpl(private val validationService: ValidationService,
                            private val organizationDataService: OrganizationDataService,
                            private val cpvRepository: CpvRepository,
                            private val currencyRepository: CurrencyRepository
) : BudgetDataService {

    override fun processEiData(cm: CommandMessage): ResponseDto {
        val lang = cm.context.language
        val country = validationService.getCountry(languageCode = lang, countryCode = cm.context.country)
        val dto = getEiData(cm)
        val cpvCode = dto.tender.classification.id
        val cpvEntity = cpvRepository.findByCpvKeyCodeAndCpvKeyLanguageCode(
                code = cpvCode,
                languageCode = cm.context.language)
                ?: throw InErrorException(ErrorType.CPV_CODE_UNKNOWN)
        dto.tender.apply {
            classification.scheme = ClassificationScheme.CPV.value()
            classification.description = cpvEntity.name
            mainProcurementCategory = cpvEntity.mainProcurementCategory
        }
        val buyer = dto.buyer ?: throw InErrorException(ErrorType.INVALID_BUYER)
        organizationDataService.processOrganization(buyer, country)

        return getResponseDto(data = dto, id = cm.id)
    }

    override fun processFsData(cm: CommandMessage): ResponseDto {
        val country = validationService.getCountry(languageCode = cm.context.language, countryCode = cm.context.country)
        val entities = currencyRepository.findByCurrencyKeyLanguageCodeAndCountries(cm.context.language, country)
        val dto = getFsData(cm)
        val currencyCode = dto.planning.budget.amount.currency
        entities.asSequence().firstOrNull { it.currencyKey?.code.equals(currencyCode) }
                ?: throw InErrorException(ErrorType.CURRENCY_UNKNOWN)

        val buyer = dto.buyer
        if (buyer != null) {
            organizationDataService.processOrganization(buyer, country)
        }

        val procuringEntity = dto.tender.procuringEntity
        if (procuringEntity != null) {
            procuringEntity.identifier.uri ?: throw InErrorException(ErrorType.INVALID_URI)
            organizationDataService.processOrganization(procuringEntity, country)
        }

        return getResponseDto(data = dto, id = cm.id)
    }

    private fun getEiData(cm: CommandMessage): EI {
        if (cm.data.size() == 0) throw InErrorException(ErrorType.INVALID_DATA, null, cm.id)
        return toObject(EI::class.java, cm.data)
    }

    private fun getFsData(cm: CommandMessage): FS {
        if (cm.data.size() == 0) throw InErrorException(ErrorType.INVALID_DATA, null, cm.id)
        return toObject(FS::class.java, cm.data)
    }
}

