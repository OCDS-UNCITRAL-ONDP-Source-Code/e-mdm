package com.procurement.mdm.service

import com.procurement.mdm.model.dto.ResponseDto
import com.procurement.mdm.model.dto.getResponseDto
import com.procurement.mdm.model.entity.getItems
import com.procurement.mdm.repository.SubMetRatRepository
import org.springframework.stereotype.Service

interface SubMetRatService {

    fun getSubMetRat(languageCode: String, internal: Boolean): ResponseDto
}

@Service
class SubMetRatServiceImpl(private val subMetRatRepository: SubMetRatRepository,
                     private val validationService: ValidationService) : SubMetRatService {

    override fun getSubMetRat(languageCode: String, internal: Boolean): ResponseDto {
        validationService.checkLanguage(languageCode, internal)
        val entities = subMetRatRepository.findBySubMetRatKeyLanguageCode(languageCode = languageCode)
        return getResponseDto(
            default = null,
            items = entities.getItems(),
            internal = internal)
    }
}

