package com.procurement.mdm.service

import com.procurement.mdm.model.dto.ResponseDto
import com.procurement.mdm.model.dto.getResponseDto
import com.procurement.mdm.model.entity.getItems
import com.procurement.mdm.repository.CpvsRepository
import org.springframework.stereotype.Service

interface CpvsService {

    fun getCpvs(languageCode: String): ResponseDto
}

@Service
class CpvsServiceImpl(private val cpvsRepository: CpvsRepository,
                      private val validationService: ValidationService) : CpvsService {

    override fun getCpvs(languageCode: String): ResponseDto {
        validationService.getLanguage(languageCode)
        val entities = cpvsRepository.findByCpvsKeyLanguageCode(languageCode = languageCode)
        return getResponseDto(items = entities.getItems())
    }
}

