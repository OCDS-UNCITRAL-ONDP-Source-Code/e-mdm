package com.procurement.mdm.controller

import com.procurement.mdm.model.dto.ResponseDto
import com.procurement.mdm.service.RegistrationSchemeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/registration-scheme")
class RegistrationSchemeController(private val registrationSchemeService: RegistrationSchemeService) {

    @GetMapping
    fun getRegistrationSchemes(@RequestParam lang: String,
                               @RequestParam country: String): ResponseEntity<ResponseDto> {
        return ResponseEntity(
                registrationSchemeService.getRegistrationScheme(
                        languageCode = lang.toLowerCase(),
                        countryCode = country.toUpperCase()),
                HttpStatus.OK)
    }
}
