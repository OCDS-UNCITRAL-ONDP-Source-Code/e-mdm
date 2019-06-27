package com.procurement.mdm.infrastructure.web.controller.address

import com.fasterxml.jackson.annotation.JsonProperty
import com.procurement.mdm.application.service.address.AddressLocalityService
import com.procurement.mdm.infrastructure.exception.LanguageRequestParameterMissingException
import com.procurement.mdm.infrastructure.web.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class AddressLocalityController(private val addressLocalityService: AddressLocalityService) {

    @GetMapping("/addresses/countries/{countryId}/regions/{regionId}/localities/{localityId}")
    @ResponseStatus(HttpStatus.OK)
    fun getCountryById(
        @PathVariable(value = "countryId") countryId: String,
        @PathVariable(value = "regionId") regionId: String,
        @PathVariable(value = "localityId") localityId: String,
        @RequestParam(value = "language", required = false) language: String?
    ): LocalityApiResponse {

        if (language == null)
            throw LanguageRequestParameterMissingException()

        val localityIdentifier = addressLocalityService.getBy(
            locality = localityId,
            country = countryId,
            region = regionId,
            language = language
        )

        return LocalityApiResponse(
            locality = Locality(
                id = localityIdentifier.id,
                description = localityIdentifier.description,
                scheme = localityIdentifier.scheme,
                uri = localityIdentifier.uri
            )
        )
    }

    class LocalityApiResponse(locality: Locality) : ApiResponse<Locality>(locality)

    data class Locality(
        @field:JsonProperty("scheme") @param:JsonProperty("scheme") val scheme: String,
        @field:JsonProperty("id") @param:JsonProperty("id") val id: String,
        @field:JsonProperty("description") @param:JsonProperty("description") val description: String,
        @field:JsonProperty("uri") @param:JsonProperty("uri") val uri: String
    )
}
