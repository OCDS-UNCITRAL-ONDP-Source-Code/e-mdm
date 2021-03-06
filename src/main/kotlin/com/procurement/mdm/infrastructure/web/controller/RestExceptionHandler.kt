package com.procurement.mdm.infrastructure.web.controller

import com.procurement.mdm.application.exception.ApplicationException
import com.procurement.mdm.application.exception.ClassificationLanguageNotFoundException
import com.procurement.mdm.application.exception.ClassificationNotFoundException
import com.procurement.mdm.application.exception.ClassificationTranslationNotFoundException
import com.procurement.mdm.application.exception.CountryDescriptionNotFoundException
import com.procurement.mdm.application.exception.CountryNotFoundException
import com.procurement.mdm.application.exception.CountrySchemeNotFoundException
import com.procurement.mdm.application.exception.IncorrectClassificationSchemeException
import com.procurement.mdm.application.exception.LocalityDescriptionNotFoundException
import com.procurement.mdm.application.exception.LocalityNotFoundException
import com.procurement.mdm.application.exception.LocalityNotLinkedToRegionException
import com.procurement.mdm.application.exception.LocalitySchemeNotFoundException
import com.procurement.mdm.application.exception.OrganizationScaleNotFoundException
import com.procurement.mdm.application.exception.OrganizationSchemeNotFoundException
import com.procurement.mdm.application.exception.RegionDescriptionNotFoundException
import com.procurement.mdm.application.exception.RegionNotFoundException
import com.procurement.mdm.application.exception.RegionNotLinkedToCountryException
import com.procurement.mdm.application.exception.RegionSchemeNotFoundException
import com.procurement.mdm.application.exception.UnitLanguageNotFoundException
import com.procurement.mdm.application.exception.UnitNotFoundException
import com.procurement.mdm.application.exception.UnitTranslationNotFoundException
import com.procurement.mdm.domain.exception.CountryUnknownException
import com.procurement.mdm.domain.exception.DomainException
import com.procurement.mdm.domain.exception.InvalidCountryCodeException
import com.procurement.mdm.domain.exception.InvalidCountrySchemeException
import com.procurement.mdm.domain.exception.InvalidCriterionCodeException
import com.procurement.mdm.domain.exception.InvalidLanguageCodeException
import com.procurement.mdm.domain.exception.InvalidLocalityCodeException
import com.procurement.mdm.domain.exception.InvalidLocalitySchemeException
import com.procurement.mdm.domain.exception.InvalidPhaseException
import com.procurement.mdm.domain.exception.InvalidPmdException
import com.procurement.mdm.domain.exception.InvalidRegionCodeException
import com.procurement.mdm.domain.exception.InvalidRegionSchemeException
import com.procurement.mdm.domain.exception.InvalidRequirementGroupCodeException
import com.procurement.mdm.domain.exception.LanguageUnknownException
import com.procurement.mdm.infrastructure.exception.CountryRequestParameterMissingException
import com.procurement.mdm.infrastructure.exception.CriterionRequestParameterMissingException
import com.procurement.mdm.infrastructure.exception.InfrastructureException
import com.procurement.mdm.infrastructure.exception.LanguageRequestParameterMissingException
import com.procurement.mdm.infrastructure.exception.NoHandlerUrlException
import com.procurement.mdm.infrastructure.exception.PhaseRequestParameterMissingException
import com.procurement.mdm.infrastructure.exception.PmdRequestParameterMissingException
import com.procurement.mdm.infrastructure.exception.RequestPayloadMissingException
import com.procurement.mdm.infrastructure.exception.RequirementGroupIdParameterMissingException
import com.procurement.mdm.infrastructure.exception.SchemeRequestParameterMissingException
import com.procurement.mdm.infrastructure.web.dto.ApiError
import com.procurement.mdm.infrastructure.web.dto.ErrorCode
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.CLASSIFICATION_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.CLASSIFICATION_SCHEME_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.CLASSIFICATION_TRANSLATION_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.COUNTRY_DESCRIPTION_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.COUNTRY_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.COUNTRY_REQUEST_PARAMETER_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.COUNTRY_REQUEST_PARAMETER_UNKNOWN
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.COUNTRY_SCHEME_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.CRITERION_REQUEST_PARAMETER_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INTERNAL_SERVER_ERROR
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_CLASSIFICATION_LANGUAGE_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_COUNTRY_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_COUNTRY_SCHEME
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_CRITERION
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_LANGUAGE_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_LOCALITY_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_LOCALITY_SCHEME
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_PHASE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_PMD
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_REGION_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_REGION_SCHEME
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_REQUIREMENT_GROUP
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_UNIT_LANGUAGE_CODE
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.INVALID_URL
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LANGUAGE_REQUEST_PARAMETER_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LANGUAGE_REQUEST_PARAMETER_UNKNOWN
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LOCALITY_DESCRIPTION_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LOCALITY_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LOCALITY_NOT_LINKED_TO_REGION
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.LOCALITY_SCHEME_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.ORGANIZATION_SCALE_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.ORGANIZATION_SCHEME_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.PHASE_REQUEST_PARAMETER_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.PMD_REQUEST_PARAMETER_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.REGION_DESCRIPTION_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.REGION_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.REGION_NOT_LINKED_TO_COUNTRY
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.REGION_SCHEME_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.REQUEST_PAYLOAD_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.REQUIREMENT_GROUP_REQUEST_PARAMETER_MISSING
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.UNIT_NOT_FOUND
import com.procurement.mdm.infrastructure.web.dto.ErrorCode.UNIT_TRANSLATION_NOT_FOUND
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(RestExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [DomainException::class])
    fun domainExceptionHandler(exception: DomainException): ResponseEntity<ApiError> {
        val apiError: ApiError = when (exception) {
            is InvalidLanguageCodeException ->
                exception.handler(errorCode = INVALID_LANGUAGE_CODE)

            is InvalidCountryCodeException ->
                exception.handler(errorCode = INVALID_COUNTRY_CODE)

            is InvalidRegionCodeException ->
                exception.handler(errorCode = INVALID_REGION_CODE)

            is InvalidLocalityCodeException ->
                exception.handler(errorCode = INVALID_LOCALITY_CODE)

            is LanguageUnknownException ->
                exception.handler(errorCode = LANGUAGE_REQUEST_PARAMETER_UNKNOWN)

            is CountryUnknownException ->
                exception.handler(errorCode = COUNTRY_REQUEST_PARAMETER_UNKNOWN)

            is InvalidCountrySchemeException ->
                exception.handler(errorCode = INVALID_COUNTRY_SCHEME)

            is InvalidRegionSchemeException ->
                exception.handler(errorCode = INVALID_REGION_SCHEME)

            is InvalidLocalitySchemeException ->
                exception.handler(errorCode = INVALID_LOCALITY_SCHEME)

            is InvalidPhaseException ->
                exception.handler(errorCode = INVALID_PHASE)

            is InvalidPmdException ->
                exception.handler(errorCode = INVALID_PMD)

            is InvalidCriterionCodeException ->
                exception.handler(errorCode = INVALID_CRITERION)

            is InvalidRequirementGroupCodeException ->
                exception.handler(errorCode = INVALID_REQUIREMENT_GROUP)
        }

        return ResponseEntity.status(apiError.status).body(apiError)
    }

    @ExceptionHandler(value = [ApplicationException::class])
    fun applicationExceptionHandler(exception: ApplicationException): ResponseEntity<ApiError> {
        val apiError: ApiError = when (exception) {
            is CountryNotFoundException ->
                exception.handler(errorCode = COUNTRY_NOT_FOUND)

            is RegionNotFoundException ->
                exception.handler(errorCode = REGION_NOT_FOUND)

            is LocalityNotFoundException ->
                exception.handler(errorCode = LOCALITY_NOT_FOUND)

            is OrganizationSchemeNotFoundException ->
                exception.handler(errorCode = ORGANIZATION_SCHEME_NOT_FOUND)

            is OrganizationScaleNotFoundException ->
                exception.handler(errorCode = ORGANIZATION_SCALE_NOT_FOUND)

            is CountrySchemeNotFoundException ->
                exception.handler(errorCode = COUNTRY_SCHEME_NOT_FOUND)

            is RegionSchemeNotFoundException ->
                exception.handler(errorCode = REGION_SCHEME_NOT_FOUND)

            is LocalitySchemeNotFoundException ->
                exception.handler(errorCode = LOCALITY_SCHEME_NOT_FOUND)

            is RegionNotLinkedToCountryException ->
                exception.handler(errorCode = REGION_NOT_LINKED_TO_COUNTRY)

            is LocalityNotLinkedToRegionException ->
                exception.handler(errorCode = LOCALITY_NOT_LINKED_TO_REGION)

            is CountryDescriptionNotFoundException ->
                exception.handler(errorCode = COUNTRY_DESCRIPTION_NOT_FOUND)

            is RegionDescriptionNotFoundException ->
                exception.handler(errorCode = REGION_DESCRIPTION_NOT_FOUND)

            is LocalityDescriptionNotFoundException ->
                exception.handler(errorCode = LOCALITY_DESCRIPTION_NOT_FOUND)

            is ClassificationLanguageNotFoundException ->
                exception.handler(errorCode = INVALID_CLASSIFICATION_LANGUAGE_CODE)

            is ClassificationNotFoundException ->
                exception.handler(errorCode = CLASSIFICATION_NOT_FOUND)

            is IncorrectClassificationSchemeException ->
                exception.handler(errorCode = CLASSIFICATION_SCHEME_NOT_FOUND)

            is ClassificationTranslationNotFoundException ->
                exception.handler(errorCode = CLASSIFICATION_TRANSLATION_NOT_FOUND)

            is UnitLanguageNotFoundException ->
                exception.handler(errorCode = INVALID_UNIT_LANGUAGE_CODE)

            is UnitNotFoundException ->
                exception.handler(errorCode = UNIT_NOT_FOUND)

            is UnitTranslationNotFoundException ->
                exception.handler(errorCode = UNIT_TRANSLATION_NOT_FOUND)
        }

        return ResponseEntity.status(apiError.status).body(apiError)
    }

    @ExceptionHandler(value = [InfrastructureException::class])
    fun infrastructureExceptionHandler(exception: InfrastructureException): ResponseEntity<ApiError> {
        val apiError: ApiError = when (exception) {
            is NoHandlerUrlException ->
                exception.handler(errorCode = INVALID_URL)

            is LanguageRequestParameterMissingException ->
                exception.handler(errorCode = LANGUAGE_REQUEST_PARAMETER_MISSING)

            is CountryRequestParameterMissingException ->
                exception.handler(errorCode = COUNTRY_REQUEST_PARAMETER_MISSING)

            is RequestPayloadMissingException ->
                exception.handler(errorCode = REQUEST_PAYLOAD_MISSING)

            is PmdRequestParameterMissingException ->
                exception.handler(errorCode = PMD_REQUEST_PARAMETER_MISSING)

            is PhaseRequestParameterMissingException ->
                exception.handler(errorCode = PHASE_REQUEST_PARAMETER_MISSING)

            is CriterionRequestParameterMissingException ->
                exception.handler(errorCode = CRITERION_REQUEST_PARAMETER_MISSING)

            is RequirementGroupIdParameterMissingException ->
                exception.handler(errorCode = REQUIREMENT_GROUP_REQUEST_PARAMETER_MISSING)

            is SchemeRequestParameterMissingException ->
                exception.handler(errorCode = CLASSIFICATION_SCHEME_NOT_FOUND)
        }

        return ResponseEntity.status(apiError.status).body(apiError)
    }

    @ExceptionHandler(value = [Exception::class])
    fun exceptionHandler(exception: Exception): ResponseEntity<ApiError> {
        log.error("Internal server error.", exception)
        val apiError: ApiError = apiError(errorCode = INTERNAL_SERVER_ERROR, description = "Internal server error.")
        return ResponseEntity.status(apiError.status).body(apiError)
    }

    private fun DomainException.handler(errorCode: ErrorCode): ApiError {
        log.warn(description)
        return apiError(errorCode = errorCode, description = this.description)
    }

    private fun ApplicationException.handler(errorCode: ErrorCode): ApiError {
        log.warn(description)
        return apiError(errorCode = errorCode, description = this.description)
    }

    private fun InfrastructureException.handler(errorCode: ErrorCode): ApiError {
        log.warn(this.description)
        return apiError(errorCode = errorCode, description = this.description)
    }

    private fun apiError(errorCode: ErrorCode, description: String) =
        ApiError(
            status = errorCode.status,
            errors = listOf(
                ApiError.Error(
                    code = errorCode,
                    description = description
                )
            )
        )
}
